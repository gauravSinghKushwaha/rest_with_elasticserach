package com.gaurav.service;

import java.util.List;

import com.gaurav.model.Driver;
import com.google.common.base.Optional;

/**
 * Service interface
 *
 * @author gkushwaha
 *
 */
public interface DriverService {

    /**
     *
     * returns drivers in radius from current latitude , longitude
     *
     * @param latitude
     * @param longitude
     * @param radius
     * @param limit
     * @return
     */

    Optional<List<Driver>> getDrivers(final double latitude, double longitude, double radius, final int limit);

    /**
     * Add drivers to system
     *
     * @param drivers
     * @return
     */
    Optional<List<Integer>> addDrivers(final List<Driver> drivers);

    /**
     * Update location of the driver
     */
    Optional<Integer> updateLocation(final Driver driver, int id);

    /**
     * Get all drivers
     * 
     * @param limit
     * @return
     */
    Optional<List<Driver>> getAllDrivers(int limit);

}
