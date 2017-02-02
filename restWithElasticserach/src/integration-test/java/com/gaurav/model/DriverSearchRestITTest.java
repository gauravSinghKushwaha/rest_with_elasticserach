package com.gaurav.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.jayway.restassured.RestAssured.given;
import static java.lang.Float.valueOf;
import static java.lang.String.valueOf;
import static java.lang.Thread.sleep;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;

public class DriverSearchRestITTest extends RestITTest {

    /**
     * @throws JsonProcessingException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    @Category(com.gaurav.test.IntegrationTest.class)
    public void testRestApis() throws JsonProcessingException, IOException, InterruptedException {
        // add drivers
        final List<Driver> drivers = getJson();
        final Response postResponse =
                given().contentType("application/json").body(drivers).when().post("/drivers").thenReturn();
        assertEquals(CREATED.getStatusCode(), postResponse.getStatusCode());

        // get all drivers
        final Response getAllResponse = given().when().get("/drivers/all?limit=10").thenReturn();
        assertEquals(OK.getStatusCode(), getAllResponse.getStatusCode());
        final List<Object> restList = getAllResponse.body().jsonPath().getList("");
        assertEquals(restList.size(), drivers.size());

        assertDriver(drivers, restList);

        // get drivers by location
        final Response getByLocResponse =
                given().when().get("/drivers?latitude=77.77&longitude=100&radius=500&limit=10").thenReturn();
        assertEquals(OK.getStatusCode(), getByLocResponse.getStatusCode());
        final List<Object> getByLocJsonList = getByLocResponse.body().jsonPath().getList("");
        assertEquals(getByLocJsonList.size(), 1);
        assertDriver(drivers, getByLocJsonList);

        // Update location
        final Response response =
                given().contentType("application/json").body(new Driver(4, "ABC4", new Location(77.77d, 100d, 0.4f)))
                .when().put("/driver/4/location").thenReturn();
        assertEquals(OK.getStatusCode(), response.getStatusCode());
        // memcached ttl
        sleep(10000);
        // get drivers by location
        final Response getByLocResponse2 =
                given().when().get("/drivers?latitude=77.77&longitude=100&radius=500&limit=10").thenReturn();
        assertEquals(OK.getStatusCode(), getByLocResponse2.getStatusCode());
        final List<Object> getByLocJsonList2 = getByLocResponse2.body().jsonPath().getList("");
        assertEquals(getByLocJsonList2.size(), 2);
        assertDriver(drivers, getByLocJsonList2);

    }

    @SuppressWarnings("rawtypes")
    private void assertDriver(final List<Driver> drivers, final List<Object> restList) {
        for (final Object object : restList) {
            final Map map = (Map) object;
            final Driver localDriver = getDriverObject(map);
            assertDriver(drivers, localDriver);
        }
    }

    private void assertDriver(final List<Driver> drivers, final Driver localDriver) {
        for (final Driver driver : drivers) {
            if (driver.getId() == localDriver.getId()) {
                assertEquals(localDriver, driver);
                return;
            }
        }
        throw new AssertionError("localDriver=" + localDriver + " does not exists");
    }

    @SuppressWarnings("rawtypes")
    private Driver getDriverObject(final Map map) {
        final Driver localDriver =
                new Driver((Integer) map.get("id"), valueOf(map.get("name")), new Location(
                        map.get("latitude") != null ? Double.valueOf(valueOf(map.get("latitude"))) : 0d,
                                map.get("longitude") != null ? Double.valueOf(valueOf(map.get("longitude"))) : 0d,
                                        map.get("accuracy") != null ? valueOf(String.valueOf(map.get("accuracy"))) : 0f));
        return localDriver;
    }

    private List<Driver> getJson() throws JsonProcessingException {
        final ArrayList<Driver> drivers =
                newArrayList(new Driver(1, "ABC1", new Location(77.77d, 100d, 0.4f)), new Driver(2, "ABC2",
                        new Location(77.71d, 100.01d, 0.4f)),
                        new Driver(3, "ABC3", new Location(77.72d, 100.02d, 0.4f)), new Driver(4, "ABC4", null));
        return drivers;
    }

}
