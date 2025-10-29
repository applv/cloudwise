package com.applv.cloudwise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record TestData(
    Integer userId,
    String userName,
    List<Map<String, String>> expectedData){

  public static Stream<TestData> getSchoolTestData() {
    List<Map<String, String>> expectedData1 = new ArrayList<>();
    expectedData1.add(Map.of("appKey", "a1", "appName", "Email",       "url", "www.outlook.com/mail"));
    expectedData1.add(Map.of("appKey", "a2", "appName", "Agenda",      "url", "www.outlook.com/agenda"));
    expectedData1.add(Map.of("appKey", "a6", "appName", "ValidSchoolParentOrganization Site", "url", "www.cloudcollege.com"));
    var p1 = new TestData(1, "John", expectedData1);

    List<Map<String, String>> expectedData2 = new ArrayList<>();
    expectedData2.add(Map.of("appKey", "a7", "appName", "ValidSchoolParentOrganization Site", "url", "www.sunschool.com"));
    var p2 = new TestData(2, "Mary", expectedData2);

    List<Map<String, String>> expectedData3 = new ArrayList<>();
    expectedData3.add(Map.of("appKey", "a5", "appName", "Intranet", "url", "www.educloudwise.com/intranet-rainbow"));
    var p3 = new TestData(3, "Peter", expectedData3);

    return Stream.of(p1, p2, p3);
  }

  public static TestData getTestData() {
    List<Map<String, String>> expectedData = new ArrayList<>();
    expectedData.add(Map.of("appKey", "a1", "appName", "Email",                 "url", "www.outlook.com/mail"));
    expectedData.add(Map.of("appKey", "a2", "appName", "Agenda",                "url", "www.outlook.com/agenda"));
    expectedData.add(Map.of("appKey", "a3", "appName", "Math4You",              "url", "www.math4you.com"));
    expectedData.add(Map.of("appKey", "a4", "appName", "Biology Naturally",     "url", "www.studyapps.com/biology-naturally"));
    expectedData.add(Map.of("appKey", "a5", "appName", "EduCloudwise Intranet", "url", "www.educloudwise.com/intranet"));
    expectedData.add(Map.of("appKey", "a6", "appName", "ValidSchoolParentOrganization Site",           "url", "www.cloudcollege.com"));

    return new TestData(1, "John", expectedData);
  }
}
