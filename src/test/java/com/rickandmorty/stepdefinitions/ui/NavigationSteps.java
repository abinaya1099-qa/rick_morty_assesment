package com.rickandmorty.stepdefinitions.ui;

import com.rickandmorty.ui.pages.AboutPage;
import com.rickandmorty.ui.pages.HomePage;
import com.rickandmorty.utils.config.ConfigReader;
import com.rickandmorty.utils.driver.DriverManager;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class NavigationSteps {
    private static final Logger log = LogManager.getLogger(NavigationSteps.class);

    private HomePage homePage = new HomePage(DriverManager.getDriver());
    private AboutPage aboutPage = new AboutPage(DriverManager.getDriver());

    @Given("I am on the Rick and Morty home page")
    public void i_am_on_home_page() {
        String url = ConfigReader.getProperty("baseUrl");
        log.info("Navigating to home page: {}", url);
        DriverManager.getDriver().get(url);
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String linkName) {
        log.info("Step: clicking '{}' link", linkName);
        homePage.clickAbout();
    }

    @Then("I should be redirected to the about page")
    public void i_should_be_redirected_to_the_about_page() {
        log.info("Step: verifying redirection to About page");
        Assert.assertTrue(aboutPage.isOnAboutPage(),
                "Redirection failed! URL: " + aboutPage.getCurrentUrl());
    }

    @Then("I should see information about the technical stuff")
    public void i_should_see_technical_info() {
        log.info("Step: verifying Technical section");
        Assert.assertTrue(aboutPage.isTechnicalInfoDisplayed(), "Technical section not found!");
    }

    @Then("I should see the author {string} mentioned")
    public void i_should_see_author_mentioned(String name) {
        log.info("Step: verifying author '{}' is mentioned on the page", name);
        Assert.assertTrue(aboutPage.isAuthorMentioned(name), name + " was not found on the page.");
    }
}