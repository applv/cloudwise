package com.applv.cloudwise.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.UserDto;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationServiceTest {

  @Autowired
  private ApplicationService appService;

  @Test
  public void getApplications_ReturnExpectedData() {
    var data = TestData.getTestData();
    var school = InstitutionDto.builder().id(1).build();
    UserDto userDto = UserDto
        .builder()
        .id(data.userId())
        .name(data.userName())
        .school(school)
        .build();
    List<ApplicationDto> apps = appService.getApplications(userDto)
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
