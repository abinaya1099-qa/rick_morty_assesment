package com.rickandmorty.api.clients;

import com.rickandmorty.api.endpoints.CharacterEndpoints;
import com.rickandmorty.utils.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterClient {
    private static final Logger log = LogManager.getLogger(CharacterClient.class);

    private final String apiBaseUrl;

    public CharacterClient() {
        this.apiBaseUrl = ConfigReader.getProperty("apiBaseUrl", "https://rickandmortyapi.com/api/");
        log.debug("CharacterClient initialized with apiBaseUrl: {}", apiBaseUrl);
    }

    public Response getCharactersByName(String name) {
        String url = apiBaseUrl + CharacterEndpoints.CHARACTERS;
        log.info("GET {} ?name={}", url, name);
        long start = System.currentTimeMillis();
        Response response = RestAssured.given()
                .queryParam("name", name)
                .get(url);
        logResponse("GET " + url + "?name=" + name, response, start);
        return response;
    }

    public Response getCharacterById(String id) {
        String url = apiBaseUrl + CharacterEndpoints.byId(id);
        log.info("GET {}", url);
        long start = System.currentTimeMillis();
        Response response = RestAssured.get(url);
        logResponse("GET " + url, response, start);
        return response;
    }

    private void logResponse(String endpoint, Response response, long startMillis) {
        long elapsed = System.currentTimeMillis() - startMillis;
        log.info("Response {} from {} in {} ms", response.getStatusCode(), endpoint, elapsed);
        if (log.isDebugEnabled()) {
            log.debug("Response body: {}", response.asString());
        }
    }
}