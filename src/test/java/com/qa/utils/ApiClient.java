package com.qa.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static void init() {
        RestAssured.baseURI = ConfigReader.get("api.url");
    }

    public static Response get(String endpoint) {
        init();
        return given().when().get(endpoint).then().extract().response();
    }

    public static Response post(String endpoint, Object body) {
        init();
        return given().header("Content-Type","application/json")
                .body(body).when().post(endpoint).then().extract().response();
    }
}
