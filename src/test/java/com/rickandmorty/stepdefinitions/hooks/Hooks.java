package com.rickandmorty.stepdefinitions.hooks;
import com.rickandmorty.api.context.ApiContext;
import com.rickandmorty.utils.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        log.info("===== Starting scenario: {} =====", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        boolean isApi = scenario.getSourceTagNames().contains("@api");
        boolean isUi = scenario.getSourceTagNames().contains("@ui");

        if (scenario.isFailed()) {
            log.error("Scenario FAILED: {}", scenario.getName());
            if (isApi) {
                attachApiContext(scenario);
            }
            if (isUi) {
                attachScreenshot(scenario);
            }
        } else {
            log.info("Scenario PASSED: {}", scenario.getName());
        }

        ApiContext.clear();
        if (isUi) {
            DriverManager.quitDriver();
        }
        log.info("===== Finished scenario: {} =====", scenario.getName());
    }

    private void attachScreenshot(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver instanceof TakesScreenshot) {
            log.info("Attaching failure screenshot");
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
    }

    private void attachApiContext(Scenario scenario) {
        Response response = ApiContext.getLastResponse();
        String endpoint = ApiContext.getLastEndpoint();
        if (response == null) {
            log.warn("No API response available to attach for scenario {}", scenario.getName());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Endpoint: ").append(endpoint).append('\n');
        sb.append("Status: ").append(response.getStatusCode()).append('\n');
        sb.append("Time: ").append(response.getTime()).append(" ms\n");
        sb.append("Body:\n").append(response.asString());
        log.info("Attaching API request/response context for failed scenario");
        scenario.attach(sb.toString().getBytes(), "text/plain", "API Failure Context");
    }
}
