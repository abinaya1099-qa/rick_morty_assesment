package com.rickandmorty.api.context;

import io.restassured.response.Response;

public class ApiContext {
    private static final ThreadLocal<Response> lastResponse = new ThreadLocal<>();
    private static final ThreadLocal<String> lastEndpoint = new ThreadLocal<>();

    public static void set(String endpoint, Response response) {
        lastEndpoint.set(endpoint);
        lastResponse.set(response);
    }

    public static Response getLastResponse() {
        return lastResponse.get();
    }

    public static String getLastEndpoint() {
        return lastEndpoint.get();
    }

    public static void clear() {
        lastResponse.remove();
        lastEndpoint.remove();
    }
}