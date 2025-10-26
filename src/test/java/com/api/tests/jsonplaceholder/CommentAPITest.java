package com.api.tests.jsonplaceholder;

import com.api.base.BaseTest;
import com.api.constants.APIConstants;
import com.api.constants.APIConstants.StatusCodes;
import com.api.models.Comment;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CommentAPITest - Tests for JSONPlaceholder Comment API endpoints
 * Focused on: Comment operations
 */
@Epic("JSONPlaceholder API")
@Feature("Comment Management")
public class CommentAPITest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CommentAPITest.class);

    @Test(priority = 1, description = "Verify getting all comments")
    @Story("Get All Comments")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that GET /comments returns all comments")
    public void testGetAllComments() {
        logger.info("Test: Get all comments");

        Response response = given()
                .spec(jsonPlaceholderSpec)
                .when()
                .get(APIConstants.JSONPlaceholder.COMMENTS)
                .then()
                .statusCode(StatusCodes.OK)
                .body("size()", equalTo(500))
                .body("[0].postId", notNullValue())
                .body("[0].email", containsString("@"))
                .extract()
                .response();

        Comment[] comments = response.as(Comment[].class);
        assertEquals(comments.length, 500, "Expected 500 comments");

        logger.info("✅ Successfully retrieved {} comments", comments.length);
    }

    @Test(priority = 2, description = "Verify getting comments by post ID")
    @Story("Get Comments By Post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify that GET /comments?postId={id} returns post comments")
    public void testGetCommentsByPostId() {
        logger.info("Test: Get comments for specific post");

        int postId = 1;

        Response response = given()
                .spec(jsonPlaceholderSpec)
                .queryParam("postId", postId)
                .when()
                .get(APIConstants.JSONPlaceholder.COMMENTS)
                .then()
                .statusCode(StatusCodes.OK)
                .body("size()", greaterThan(0))
                .body("[0].postId", equalTo(postId))
                .body("[0].email", containsString("@"))
                .extract()
                .response();

        Comment[] comments = response.as(Comment[].class);

        // Verify all comments belong to the post
        for (Comment comment : comments) {
            assertEquals(comment.getPostId(), postId,
                    "All comments should belong to post " + postId);
            assertTrue(comment.getEmail().contains("@"),
                    "Email should be valid");
        }

        logger.info("✅ Post {} has {} comments", postId, comments.length);
    }

    @Test(priority = 3, description = "Verify comment data structure")
    @Story("Validate Comment Schema")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify that comment object contains all required fields")
    public void testValidateCommentStructure() {
        logger.info("Test: Validate comment data structure");

        given()
                .spec(jsonPlaceholderSpec)
                .queryParam("postId", 1)
                .when()
                .get(APIConstants.JSONPlaceholder.COMMENTS)
                .then()
                .statusCode(StatusCodes.OK)
                .body("[0].postId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].body", notNullValue());

        logger.info("✅ Comment data structure is valid");
    }
}
