package com.rickandmorty.utils.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.io.FileInputStream;

public class ConfigReader {
    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;

    static {
        try {
            String env = System.getProperty("env", "qa");
            String path = "src/test/resources/config/" + env + ".properties";
            log.info("Loading configuration from {}", path);
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
            log.debug("Loaded {} properties for env '{}'", properties.size(), env);
        } catch (Exception e) {
            log.error("Failed to load configuration properties", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.debug("Property '{}' not found, falling back to default '{}'", key, defaultValue);
            return defaultValue;
        }
        return value;
    }
}