package com.rickandmorty.utils.driver;

import com.rickandmorty.utils.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = ConfigReader.getProperty("browser", "chrome").toLowerCase();
            boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"));
            log.info("Initializing WebDriver — browser: {}, headless: {}", browser, headless);
            driver.set(createDriver(browser, headless));
            driver.get().manage().window().maximize();
            log.info("WebDriver initialized for thread {}", Thread.currentThread().getName());
        }
        return driver.get();
    }

    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }
                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                return new FirefoxDriver(firefoxOptions);

            default:
                log.error("Unsupported browser '{}'. Supported: chrome, firefox", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            log.info("Quitting WebDriver for thread {}", Thread.currentThread().getName());
            driver.get().quit();
            driver.remove();
        }
    }
}