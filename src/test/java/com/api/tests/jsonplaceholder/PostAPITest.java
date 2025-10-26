package com.api.tests.jsonplaceholder;

import com.api.base.BaseTest;
import com.api.constants.APIConstants;
import com.api.constants.APIConstants.StatusCodes;
import com.api.models.Post;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class PostAPITest extends BaseTest {

    @Test(priority = 1, description = "Verify creating a new post")
    public void testCreatePost() {
        System.out.println("🚀 Test: Create new post");

        Post newPost = new Post(1, "Test Post Title", "This is test post body");

        Post createdPost = given()
                .spec(jsonPlaceholderSpec)
                .body(newPost)
        .when()
                .post(APIConstants.JSONPlaceholder.POSTS)
        .then()
                .statusCode(StatusCodes.CREATED)
                .body("userId", equalTo(1))
                .body("title", equalTo("Test Post Title"))
                .body("id", notNullValue())
                .extract()
                .as(Post.class);

        assertNotNull(createdPost.getId());
        assertEquals(createdPost.getTitle(), "Test Post Title");

        System.out.println("✅ Post created with ID: " + createdPost.getId());
    }

    @Test(priority = 2, description = "Verify getting all posts")
    public void testGetAllPosts() {
        System.out.println("🚀 Test: Get all posts");

        Response response = given()
                .spec(jsonPlaceholderSpec)
        .when()
                .get(APIConstants.JSONPlaceholder.POSTS)
        .then()
                .statusCode(StatusCodes.OK)
                .body("size()", equalTo(100))
                .extract()
                .response();

        Post[] posts = response.as(Post[].class);
        assertEquals(posts.length, 100);

        System.out.println("✅ Retrieved " + posts.length + " posts");
    }

    @Test(priority = 3, description = "Verify updating a post")
    public void testUpdatePost() {
        System.out.println("🚀 Test: Update post");

        Post updatedPost = new Post(1, "Updated Title", "Updated body content");
        updatedPost.setId(1);

        given()
                .spec(jsonPlaceholderSpec)
                .pathParam("id", 1)
                .body(updatedPost)
        .when()
                .put(APIConstants.JSONPlaceholder.POSTS_BY_ID)
        .then()
                .statusCode(StatusCodes.OK)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body content"));

        System.out.println("✅ Post updated successfully");
    }

    @Test(priority = 4, description = "Verify deleting a post")
    public void testDeletePost() {
        System.out.println("🚀 Test: Delete post");

        given()
                .spec(jsonPlaceholderSpec)
                .pathParam("id", 1)
        .when()
                .delete(APIConstants.JSONPlaceholder.POSTS_BY_ID)
        .then()
                .statusCode(StatusCodes.OK);

        System.out.println("✅ Post deleted successfully");
    }
}