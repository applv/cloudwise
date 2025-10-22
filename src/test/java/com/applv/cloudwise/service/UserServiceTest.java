package com.applv.cloudwise.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.applv.cloudwise.dto.ApplicationDto;
import com.applv.cloudwise.dto.InstitutionDto;
import com.applv.cloudwise.dto.UserDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @ParameterizedTest
  @MethodSource("testData")
  public void getUserSchoolApplications_ReturnExpectedData(TestData data) {
    var school = InstitutionDto.builder().id(1).build();
    UserDto userDto = UserDto
        .builder()
        .id(data.userId)
        .name(data.userName)
        .school(school)
        .build();
    List<ApplicationDto> apps = userService.getUserSchoolApplications(userDto)
        .stream()
        .sorted(Comparator.comparingInt(app -> Integer.parseInt(app.getAppId().replaceAll("[^0123456789]", ""))))
        .toList();

    assertThat(apps).hasSize(data.expectedData.size());

    for (int i = 0; i < data.expectedData.size(); i++) {
      var values = data.expectedData.get(i);
      var app = apps.get(i);
      assertThat(app.getName()).isEqualTo(values.get("appName"));
      assertThat(app.getAppId()).isEqualTo(values.get("appId"));
      assertThat(app.getUrl()).isEqualTo(values.get("url"));
    }
  }

  private static Stream<TestData> testData() {

    List<Map<String, String>> expectedData1 = new ArrayList<>();
    expectedData1.add(Map.of("appId", "a1", "appName", "Email",       "url", "www.outlook.com/mail"));
    expectedData1.add(Map.of("appId", "a2", "appName", "Agenda",      "url", "www.outlook.com/agenda"));
    expectedData1.add(Map.of("appId", "a6", "appName", "School Site", "url", "www.cloudcollege.com"));
    var p1 = new TestData(1, "John", expectedData1);

    List<Map<String, String>> expectedData2 = new ArrayList<>();
    expectedData2.add(Map.of("appId", "a7", "appName", "School Site", "url", "www.sunschool.com"));
    var p2 = new TestData(2, "Mary", expectedData2);

    List<Map<String, String>> expectedData3 = new ArrayList<>();
    expectedData3.add(Map.of("appId", "a5", "appName", "Intranet", "url", "www.educloudwise.com/intranet-rainbow"));
    var p3 = new TestData(3, "Peter", expectedData3);

    return Stream.of(p1, p2, p3);
  }

  record TestData(
    Integer userId,
    String userName,
    List<Map<String, String>> expectedData){
  }
}
