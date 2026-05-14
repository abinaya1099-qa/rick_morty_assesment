package com.rickandmorty.utils.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private static final int MAX_RETRY_COUNT = 1;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String scenarioName = result.getName();

        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            log.warn("Retrying '{}' (scenario: {}) — attempt {} of {}",
                    testName, scenarioName, retryCount, MAX_RETRY_COUNT);
            return true;
        }
        log.error("'{}' (scenario: {}) failed after {} retry attempt(s)",
                testName, scenarioName, MAX_RETRY_COUNT);
        return false;
    }
}