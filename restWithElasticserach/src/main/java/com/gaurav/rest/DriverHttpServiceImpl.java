package com.gaurav.rest;

import static com.gaurav.perf.PerformanceLabels.HTTP_GET_ALL_DRIVERS;
import static com.gaurav.perf.PerformanceLabels.HTTP_GET_DRIVERS;
import static com.gaurav.perf.PerformanceLabels.HTTP_POST_ADD_DRIVER;
import static com.gaurav.perf.PerformanceLabels.HTTP_PUT_DRIVER_LOC;
import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.valueOf;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.ws.rs.core.Response;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.model.Driver;
import com.gaurav.service.DriverService;
import com.gaurav.validate.Validate;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;

/**
 * Rest Implementation
 *
 * @author gkushwaha
 *
 */
@Service("driverHttpService")
public class DriverHttpServiceImpl implements DriverHttpService {

    private static final Joiner WHITE_SPACE_JOINER = on(" ").skipNulls();
    private static final Logger LOGGER = getLogger(DriverHttpServiceImpl.class);
    private final DriverService driverService;
    private final Validate validator;

    @Autowired
    public DriverHttpServiceImpl(final DriverService driverService, final Validate validator) {
        this.driverService = checkNotNull(driverService, "The driverService cannot be null");
        this.validator = checkNotNull(validator, "The validator cannot be null");
    }

    @Override
    @Profiled(tag = HTTP_GET_DRIVERS, logFailuresSeparately = true)
    public Response getDrivers(final double latitude, final double longitude, final double radius, final int limit) {
        LOGGER.debug("latitude ={},longitude ={},radius ={},limit ={},", latitude, longitude, radius, limit);
        if (!validator.validateLatitude(latitude)) {
            return status(BAD_REQUEST).entity(new String("Latitude should be between +/- 90")).build();
        }
        if (!validator.validateLongitude(longitude)) {
            return status(BAD_REQUEST).entity(new String("longitude should be between +/- 180")).build();
        }
        final Optional<List<Driver>> drivers =
                driverService.getDrivers(latitude, longitude, validator.getRadius(radius), validator.getLimit(limit));
        LOGGER.debug("drivers ={} ", drivers);
        return ok().entity(drivers.get()).build();
    }

    @Override
    @Profiled(tag = HTTP_GET_ALL_DRIVERS, logFailuresSeparately = true)
    public Response getAllDrivers(final int limit) {
        final Optional<List<Driver>> drivers = driverService.getAllDrivers(validator.getLimit(limit));
        LOGGER.debug("drivers ={} ", drivers);
        return ok().entity(drivers.get()).build();
    }

    @Override
    @Profiled(tag = HTTP_POST_ADD_DRIVER, logFailuresSeparately = true)
    public Response addDrivers(final List<Driver> drivers) {
        LOGGER.debug("drivers = {}", drivers);
        final Optional<List<Integer>> driverIds = driverService.addDrivers(drivers);
        LOGGER.debug("driverIds ={} ", driverIds);
        return status(CREATED).entity(WHITE_SPACE_JOINER.join(driverIds.get())).build();
    }

    @Override
    @Profiled(tag = HTTP_PUT_DRIVER_LOC, logFailuresSeparately = true)
    public Response updateLocation(final Driver driver, final int id) {
        LOGGER.debug("driver = {}", driver);
        if (!validator.validateId(id)) {
            return status(NOT_FOUND).entity(valueOf("Inavalid userId")).build();
        }
        if (!validator.validateLatitude(driver.getLocation())) {
            return status(NOT_ACCEPTABLE).entity(valueOf("Latitude should be between +/- 90")).build();
        }
        if (!validator.validateLongitude(driver.getLocation())) {
            return status(NOT_ACCEPTABLE).entity(valueOf("longitude should be between +/- 180")).build();
        }

        final Optional<Integer> updatedIds = driverService.updateLocation(driver, id);
        return updatedIds.get() > 0 ? status(OK).entity(updatedIds.get()).build() : status(NOT_FOUND).entity(
                valueOf("Inavalid userId")).build();
    }
}
