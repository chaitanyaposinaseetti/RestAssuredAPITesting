package com.api.base;

import com.api.constants.APIConstants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected RequestSpecification jsonPlaceholderSpec;
    protected RequestSpecification reqResSpec;
    protected RequestSpecification httpBinSpec;

    @BeforeClass

    public void setup(){

        System.out.println("Settig up specifications...");

        //JSONPlaceholder API Spec

        jsonPlaceholderSpec = new RequestSpecBuilder()
                .setBaseUri(APIConstants.JSONPLACEHOLDER_BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();

        // ReqRes API Spec
        reqResSpec = new RequestSpecBuilder()
                .setBaseUri(APIConstants.REQRES_BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();

        // HTTPBin API Spec
        httpBinSpec = new RequestSpecBuilder()
                .setBaseUri(APIConstants.HTTPBIN_BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();

        System.out.println("✅ Setup complete!");
    }
}

