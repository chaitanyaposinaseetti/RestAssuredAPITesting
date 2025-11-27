package com.api.tests.reqres;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MyFirstAPITest {

    @Test

    public void testGetAllUsers(){
    //Given:Setup base url
    String baseUrl = "https://jsonplaceholder.typicode.com";

    //When: Make GET request
    Response response = given()
            .baseUri(baseUrl)
            .when()
            .get("/users")
            .then()
            .statusCode(200)
            .body("size()", equalTo(10))
            .extract()
            .response();

    //print response
    System.out.println("Response: "+response.asString());
  }

}
