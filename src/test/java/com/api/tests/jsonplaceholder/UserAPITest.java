package com.api.tests.jsonplaceholder;

import com.api.base.BaseTest;
import com.api.constants.APIConstants;
import com.api.constants.APIConstants.StatusCodes;
import com.api.models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class UserAPITest extends BaseTest {

    @Test(priority = 1, description = "Verify getting all users returns 10 users")
    public void testGetAllUsers() {
        System.out.println("🚀 Test: Get all users from JSONPlaceholder API");

        Response response = given()
                .spec(jsonPlaceholderSpec)
                .when()
                .get(APIConstants.JSONPlaceholder.USERS)
                .then()
                .statusCode(StatusCodes.OK)
                .body("size()", equalTo(10))
                .body("[0].id", notNullValue())
                .body("[0].email", containsString("@"))
                .extract()
                .response();

        // Convert to POJO
        User[] users = response.as(User[].class);
        assertEquals(users.length, 10, "Expected 10 users");

        System.out.println("✅ SUCCESS! Found " + users.length + " users");
        System.out.println("First user: " + users[0].getName());
    }

    @Test(priority = 2, description = "Verify getting a single user by ID")
    public void testGetSingleUser() {
        System.out.println("🚀 Test: Get single user by ID");

        int userId = 1;

        User user = given()
                .spec(jsonPlaceholderSpec)
                .pathParam("id", userId)
                .when()
                .get(APIConstants.JSONPlaceholder.USERS_BY_ID)
                .then()
                .statusCode(StatusCodes.OK)
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("email", containsString("@"))
                .extract()
                .as(User.class);

        // Assertions
        assertEquals(user.getId(), userId);
        assertNotNull(user.getName());
        assertTrue(user.getEmail().contains("@"));

        System.out.println("✅ User found: " + user.getName() + " (" + user.getEmail() + ")");
    }

    @Test(priority = 3, description = "Verify 404 error for non-existent user")
    public void testGetNonExistentUser() {
        System.out.println("🚀 Test: Get non-existent user (should return 404)");

        int invalidUserId = 9999;

        given()
                .spec(jsonPlaceholderSpec)
                .pathParam("id", invalidUserId)
                .when()
                .get(APIConstants.JSONPlaceholder.USERS_BY_ID)
                .then()
                .statusCode(StatusCodes.NOT_FOUND);

        System.out.println("✅ Correctly returned 404 for invalid user ID");
    }
}