package com.gaurav.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class DriverITTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    @Category(com.gaurav.test.IntegrationTest.class)
    public void testReadPayload() throws JsonProcessingException, IOException {
        final ObjectReader reader = mapper.reader(Driver.class);
        final Driver driver1 = new Driver(1, "ABC", new Location(77.77d, 100d, 0.4f));
        final String json = mapper.writeValueAsString(driver1);
        final Driver driver2 = (Driver) reader.readValue(json);
        assertNotNull(driver1);
        assertEquals(driver1, driver2);
    }

}
