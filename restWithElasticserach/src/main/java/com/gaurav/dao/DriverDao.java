package com.gaurav.dao;

import java.util.List;

import com.gaurav.model.Driver;

/**
 * The data access object for {@link Driver}.
 */
public interface DriverDao {

    List<Driver> getDrivers(float latitude, float longitude, int radius, int limit);

    List<Integer> addDrivers(final List<Driver> drivers);

    Integer updateLocation(final Driver driver, int id);

}
