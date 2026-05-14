package com.rickandmorty.utils.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class TestDataReader {
    private static final Logger log = LogManager.getLogger(TestDataReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String DATA_FILE = "src/test/resources/testdata/ui-data.json";
    private static JsonNode root;

    static {
        try {
            File file = new File(DATA_FILE);
            log.info("Loading UI test data from {}", file.getAbsolutePath());
            root = MAPPER.readTree(file);
        } catch (IOException e) {
            log.error("Failed to load UI test data from {}", DATA_FILE, e);
            root = MAPPER.createObjectNode();
        }
    }

    public static String getString(String dotPath) {
        JsonNode node = root;
        for (String segment : dotPath.split("\\.")) {
            if (node == null || node.isMissingNode()) {
                break;
            }
            node = node.get(segment);
        }
        if (node == null || node.isMissingNode() || node.isNull()) {
            log.warn("Test data key '{}' not found", dotPath);
            return null;
        }
        return node.asText();
    }
}