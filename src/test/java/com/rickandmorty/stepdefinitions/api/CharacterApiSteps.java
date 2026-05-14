package com.rickandmorty.stepdefinitions.api;

import com.rickandmorty.api.clients.CharacterClient;
import com.rickandmorty.api.context.ApiContext;
import com.rickandmorty.api.models.CharacterResponse;
import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class CharacterApiSteps {
    private static final Logger log = LogManager.getLogger(CharacterApiSteps.class);

    private final CharacterClient characterClient = new CharacterClient();
    private Response response;

    @When("I search for characters with name {string}")
    public void searchByName(String name) {
        log.info("Step: search characters by name='{}'", name);
        response = characterClient.getCharactersByName(name);
        ApiContext.set("GET /character?name=" + name, response);
    }

    @When("I search for character ID {string}")
    public void searchById(String id) {
        log.info("Step: search character by id='{}'", id);
        response = characterClient.getCharacterById(id);
        ApiContext.set("GET /character/" + id, response);
    }

    @Then("the API response status should be {int}")
    public void verifyStatus(int statusCode) {
        log.info("Step: verifying status code is {}", statusCode);
        Assert.assertEquals(response.getStatusCode(), statusCode, "API Status Code Mismatch!");
    }

    @Then("the results should contain characters with {string} in their name")
    public void verifyNameInResults(String expectedName) {
        log.info("Step: verifying results contain '{}'", expectedName);
        CharacterResponse charResponse = response.as(CharacterResponse.class);
        boolean found = charResponse.getResults().stream()
                .anyMatch(c -> c.getName().toLowerCase().contains(expectedName.toLowerCase()));
        log.debug("Match found in results: {}", found);
        Assert.assertTrue(found, "No characters found containing name: " + expectedName);
    }

    @Then("the response should contain error message {string}")
    public void verifyErrorMessage(String expectedError) {
        log.info("Step: verifying error message '{}'", expectedError);
        String actualError = response.jsonPath().getString("error");
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");
    }

    @Then("the response should match JSON schema {string}")
    public void verifyMatchesSchema(String schemaName) {
        String schemaPath = "schemas/" + schemaName + ".json";
        log.info("Step: validating response against schema '{}'", schemaPath);
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
        log.debug("Schema validation passed for '{}'", schemaPath);
    }
}