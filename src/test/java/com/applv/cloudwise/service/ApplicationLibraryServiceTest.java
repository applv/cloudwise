package com.applv.cloudwise.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.applv.cloudwise.dto.ApplicationDto;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class ApplicationLibraryServiceTest {

  @Autowired
  private ApplicationLibraryService service;

  @Test
  @DirtiesContext
  @Sql(scripts = {"classpath:db/data-test.sql"})
  public void getUserApplications_ReturnExpectedData() {
    var data = TestData.getTestData();

    List<ApplicationDto> apps = service.getUserApplications(data.userName())
        .stream()
        .sorted(Comparator.comparingInt(app -> Integer.parseInt(app.getAppKey().replaceAll("[^0123456789]", ""))))
        .toList();

    assertThat(apps).hasSize(data.expectedData().size());

    for (int i = 0; i < data.expectedData().size(); i++) {
      var values = data.expectedData().get(i);
      var app = apps.get(i);
      assertThat(app.getName()).isEqualTo(values.get("appName"));
      assertThat(app.getAppKey()).isEqualTo(values.get("appKey"));
      assertThat(app.getUrl()).isEqualTo(values.get("url"));
    }
  }
}
