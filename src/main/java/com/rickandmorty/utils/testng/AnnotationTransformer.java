package com.rickandmorty.utils.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {
    private static final Logger log = LogManager.getLogger(AnnotationTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
        if (testMethod != null) {
            log.debug("Attached RetryAnalyzer to {}.{}",
                    testMethod.getDeclaringClass().getSimpleName(),
                    testMethod.getName());
        }
    }
}