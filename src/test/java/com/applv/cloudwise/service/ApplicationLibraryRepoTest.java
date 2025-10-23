package com.applv.cloudwise.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.repository.ApplicationLibraryRepo;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationLibraryRepoTest {

  @Autowired
  private ApplicationLibraryRepo repository;

  @ParameterizedTest
  @MethodSource("testData")
  public void getUserSchoolApplications_ReturnSchoolRelatedApplications(TestData data) {
    List<ApplicationDto> apps = repository.getUserSchoolApplications(data.userName())
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

  @Test
  public void getUserApplications_ReturnExpectedData() {
    var data = TestData.getTestData();

    List<ApplicationDto> apps = repository.getUserApplications(data.userName())
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

  private static Stream<TestData> testData() {
    return TestData.getSchoolTestData();
  }
}
