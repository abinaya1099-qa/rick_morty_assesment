package com.rickandmorty.ui.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private By aboutLink = By.xpath("//nav//a[contains(normalize-space(@href), 'about')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAbout() {
        log.info("Clicking About link in top navigation");
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
        log.debug("About link click dispatched");
    }
}