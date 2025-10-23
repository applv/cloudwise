package com.applv.cloudwise.repository;

import static com.applv.cloudwise.entity.Constants.ORGANIZATION;
import static com.applv.cloudwise.entity.Constants.ROOT;
import static com.applv.cloudwise.entity.Constants.SCHOOL;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.InstitutionTypeDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicationLibraryRepoImpl implements ApplicationLibraryRepo {

  private final static String USER_APPLICATION_SQL = """
      with   user_data as (
              select cast(:user_name as varchar(200)) as user_name
              ),
          applications as (
              select type.id       as type_id,
                     inst.id       as inst_id,
                     app.id        as app_id,
                     type.name     as type_name,
                     inst.name     as institution_name,
                     app.name      as app_name,
                     app.app_id    as app_key,
                     app.url       as url,
                     type.priority as priority
                from institution_type type
                join institution inst on inst.type_id       = type.id
                join application app  on app.institution_id = inst.id
               where type.name in (:school, :organisation, :root)
              ),
          school as (
              select *
                from applications app
               where type_name = :school
                 and exists (select 1 from users
                                     where school_id = app.inst_id
                                       and users.name = (select user_name from user_data))
          ),
           organisation as (
              select *
                from applications
               where type_name = :organisation
                 and app_key not in (select app_key from school)
           ),
           root as (
              select *
                from applications
               where type_name = :root
                 and app_key not in (select app_key from organisation)
                 and app_key not in (select app_key from school)
           )
        select * from school
      union all
        select * from organisation
      union all
        select * from root
      order by app_key
      """;

  private final NamedParameterJdbcOperations jdbcOperations;

  @Override
  public List<ApplicationDto> getUserApplications(String userName) {

    var paramSource = new MapSqlParameterSource();

    paramSource.addValue("user_name",    userName,     SqlTypes.VARCHAR);
    paramSource.addValue("school",       SCHOOL,       SqlTypes.VARCHAR);
    paramSource.addValue("organisation", ORGANIZATION, SqlTypes.VARCHAR);
    paramSource.addValue("root",         ROOT,         SqlTypes.VARCHAR);

    return jdbcOperations.query(USER_APPLICATION_SQL, paramSource,new ApplicationRowMapper());
  }

  @Override
  public List<ApplicationDto> getUserSchoolApplications(String userName) {

    var paramSource = new MapSqlParameterSource();

    paramSource.addValue("user_name",    userName,      SqlTypes.VARCHAR);
    paramSource.addValue("school",       SCHOOL,        SqlTypes.VARCHAR);
    paramSource.addValue("organisation", SqlTypes.NULL, SqlTypes.VARCHAR);
    paramSource.addValue("root",         SqlTypes.NULL, SqlTypes.VARCHAR);

    return jdbcOperations.query(USER_APPLICATION_SQL, paramSource,new ApplicationRowMapper());
  }

  private static class ApplicationRowMapper implements RowMapper<ApplicationDto> {

    @Override
    public ApplicationDto mapRow(ResultSet rs, int rowNum) throws SQLException {

      var type = InstitutionTypeDto
          .builder()
          .id(rs.getInt("type_id"))
          .name(rs.getString("type_name"))
          .appPriority(rs.getInt("priority"))
          .build();

      var institution = InstitutionDto
          .builder()
          .id(rs.getInt("inst_id"))
          .name(rs.getString("institution_name"))
          .type(type)
          .build();

      return ApplicationDto
          .builder()
          .id(rs.getInt("app_id"))
          .appId(rs.getString("app_key"))
          .name(rs.getString("app_name"))
          .url(rs.getString("url"))
          .institution(institution)
          .build();
    }
  }
}
