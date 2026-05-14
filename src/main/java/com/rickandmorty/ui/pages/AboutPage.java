package com.rickandmorty.ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AboutPage {
    private static final Logger log = LogManager.getLogger(AboutPage.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private By technicalHeading = By.xpath("//h3[contains(text(),'Technical')]");

    public AboutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        log.debug("Current URL: {}", url);
        return url;
    }

    public boolean isOnAboutPage() {
        log.info("Waiting for redirection to About page");
        try {
            boolean redirected = wait.until(ExpectedConditions.urlContains("/about"));
            log.info("Successfully landed on About page: {}", driver.getCurrentUrl());
            return redirected;
        } catch (TimeoutException e) {
            log.error("Redirection to About page timed out. Current URL: {}", driver.getCurrentUrl());
            return false;
        }
    }

    public boolean isTechnicalInfoDisplayed() {
        log.info("Verifying 'Technical' section is displayed");
        boolean displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(technicalHeading)).isDisplayed();
        log.debug("Technical section displayed: {}", displayed);
        return displayed;
    }

    public boolean isAuthorMentioned(String name) {
        log.info("Verifying author '{}' is mentioned on the page", name);
        By authorText = By.xpath("//*[contains(text(),'" + name + "')]");
        boolean present = wait.until(ExpectedConditions.presenceOfElementLocated(authorText)).isDisplayed();
        log.debug("Author element present: {}", present);
        return present;
    }
}