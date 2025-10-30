package com.applv.cloudwise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record TestData(
    Integer userId,
    String userName,
    List<Map<String, String>> expectedData){

  public static TestData getTestData() {
    List<Map<String, String>> expectedData = new ArrayList<>();
    expectedData.add(Map.of("appKey", "a1000", "appName", "Email",                 "url", "www.outlook.com/mail"));
    expectedData.add(Map.of("appKey", "a2000", "appName", "Agenda",                "url", "www.outlook.com/agenda"));
    expectedData.add(Map.of("appKey", "a3000", "appName", "Math4You",              "url", "www.math4you.com"));
    expectedData.add(Map.of("appKey", "a4000", "appName", "Biology Naturally",     "url", "www.studyapps.com/biology-naturally"));
    expectedData.add(Map.of("appKey", "a5000", "appName", "EduCloudwise Intranet", "url", "www.educloudwise.com/intranet"));
    expectedData.add(Map.of("appKey", "a6000", "appName", "School Site",           "url", "www.cloudcollege.com"));

    return new TestData(1000, "John", expectedData);
  }
}
