package com.api.constants;

public class APIConstants {

    // Base URLs
    public static final String JSONPLACEHOLDER_BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String REQRES_BASE_URL = "https://reqres.in/api";
    public static final String HTTPBIN_BASE_URL = "https://httpbin.org";

    // JSONPlaceholder Endpoints
    public static class JSONPlaceholder {
        public static final String USERS = "/users";
        public static final String USERS_BY_ID = "/users/{id}";
        public static final String POSTS = "/posts";
        public static final String POSTS_BY_ID = "/posts/{id}";
    }

    // ReqRes Endpoints
    public static class ReqRes {
        public static final String USERS = "/users";
        public static final String USERS_BY_ID = "/users/{id}";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";
    }

    // HTTPBin Endpoints
    public static class HTTPBin {
        public static final String GET = "/get";
        public static final String POST = "/post";
        public static final String PUT = "/put";
        public static final String DELETE = "/delete";
    }

    // Status Codes
    public static class StatusCodes {
        public static final int OK = 200;
        public static final int CREATED = 201;
        public static final int NO_CONTENT = 204;
        public static final int BAD_REQUEST = 400;
        public static final int NOT_FOUND = 404;
    }
}