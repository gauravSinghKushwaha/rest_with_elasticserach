package com.gaurav.model;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.port;
import static java.lang.Integer.valueOf;
import static java.lang.System.getProperty;

import org.junit.BeforeClass;

public abstract class RestITTest {
    public static final int DEFAULT_PORT = 9090;
    public static final String DEFAULT_HOST = "http://localhost";
    public static final String DEFAULT_BASE = "/driversearch/resources/rest/";

    /**
     * Used for setting base url, port , host for rest assured tests
     */
    @BeforeClass
    public static void setup() {
        final String portStr = getProperty("server.port");
        if (portStr == null) {
            port = DEFAULT_PORT;
        } else {
            port = valueOf(portStr);
        }

        basePath = getProperty("server.base");
        if (basePath == null) {
            basePath = DEFAULT_BASE;
        }

        baseURI = getProperty("server.host");
        if (baseURI == null) {
            baseURI = DEFAULT_HOST;
        }

    }
}
