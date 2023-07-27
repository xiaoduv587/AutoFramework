package com.xiaodu.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * @author xiaodu
 */
public final class BaseRequestSpecification {

    private BaseRequestSpecification() {
    }

    //  private static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
    private static final String BASE_URL = "https://www.baidu.com/";


    public static RequestSpecification getDefaultRequestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .baseUri(BASE_URL);
    }


    public static RequestSpecification getUserTokenRequest(Map<String, String> headerMap) {
        return RestAssured
                .given()
                .headers(headerMap)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

}
