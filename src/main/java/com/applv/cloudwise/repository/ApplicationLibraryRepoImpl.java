package com.applv.cloudwise.repository;

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
      with school_app as (
                 select app.id                as app_id,
                        app.name              as app_name,
                        app.app_key           as app_key,
                        app.url               as url,
                        inst.id               as institution_id,
                        inst.name             as institution_name,
                        inst.type_id          as type_id,
                        type.name             as institution_type,
                        inst.school_parent_id as parent_organization_id
                  from users users
                  join institution inst      on inst.id            = users.school_id
                  join institution_type type on type.id            = inst.type_id
                                            and type.name          = :school
                  join application app       on app.institution_id = inst.id
                 where users.name = :user_name
             ),
             organization_app as (
                 select app.id        as app_id,
                        app.name      as app_name,
                        app.app_key   as app_key,
                        app.url       as url,
                        inst.id       as institution_id,
                        inst.name     as institution_name,
                        inst.type_id  as type_id,
                        type.name     as institution_type,
                        null          as parent_organization_id
                  from institution inst
                  join institution_type type on type.id            = inst.type_id
                  join application app       on app.institution_id = inst.id
                 where inst.id in (select parent_organization_id from school_app)
                   and app.app_key not in (select app_key from school_app)
             ),
             root_app as (
                 select app.id        as app_id,
                        app.name      as app_name,
                        app.app_key   as app_key,
                        app.url       as url,
                        inst.id       as institution_id,
                        inst.name     as institution_name,
                        inst.type_id  as type_id,
                        type.name     as institution_type,
                        null          as parent_organization_id
                  from application app
                  join institution inst      on inst.id          = app.institution_id
                  join institution_type type on type.id          = inst.type_id
                  where app_key not in (select app_key from school_app
                                         union all
                                        select app_key from organization_app)
                    and type.name = :root
                    and exists(select 1 from school_app limit 1)
             )
          select * from school_app
      union all
          select * from organization_app
      union all
          select * from root_app
      order by app_key
      """;

  private final NamedParameterJdbcOperations jdbcOperations;

  @Override
  public List<ApplicationDto> getUserApplications(String userName) {

    var paramSource = new MapSqlParameterSource();

    paramSource.addValue("user_name",    userName,     SqlTypes.VARCHAR);
    paramSource.addValue("school",       SCHOOL,       SqlTypes.VARCHAR);
    paramSource.addValue("root",         ROOT,         SqlTypes.VARCHAR);

    return jdbcOperations.query(USER_APPLICATION_SQL, paramSource, new ApplicationRowMapper());
  }

  private static class ApplicationRowMapper implements RowMapper<ApplicationDto> {

    @Override
    public ApplicationDto mapRow(ResultSet rs, int rowNum) throws SQLException {

      var type = InstitutionTypeDto
          .builder()
          .id(rs.getInt("type_id"))
          .name(rs.getString("institution_type"))
          .build();

      var institution = InstitutionDto
          .builder()
          .id(rs.getInt("institution_id"))
          .name(rs.getString("institution_name"))
          .type(type)
          .build();

      return ApplicationDto
          .builder()
          .id(rs.getInt("app_id"))
          .appKey(rs.getString("app_key"))
          .name(rs.getString("app_name"))
          .url(rs.getString("url"))
          .institution(institution)
          .build();
    }
  }
}
