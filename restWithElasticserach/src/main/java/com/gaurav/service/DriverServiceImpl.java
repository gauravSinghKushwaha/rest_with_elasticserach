package com.gaurav.service;

import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.join;
import static java.lang.String.valueOf;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.cache.CacheService;
import com.gaurav.dao.DriverDao;
import com.gaurav.model.Driver;
import com.google.common.base.Optional;

@Service("driverService")
public class DriverServiceImpl implements DriverService {

    private final DriverDao dao;
    private final CacheService cacheService;

    private static final Logger LOGGER = getLogger(DriverServiceImpl.class);
    private static final String SEARCH_DRIVER = "DRIVER::";

    @Autowired
    public DriverServiceImpl(final DriverDao dao, final CacheService cacheService) {
        this.dao = checkNotNull(dao);
        this.cacheService = checkNotNull(cacheService);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<Driver>> getDrivers(final double latitude, final double longitude, final int radius,
            final int limit) {
        final String key =
                join("::", SEARCH_DRIVER, valueOf(latitude), valueOf(longitude), valueOf(radius), valueOf(limit));
        final Optional<List<Driver>> value = (Optional<List<Driver>>) cacheService.get(key);
        LOGGER.debug("value = {}", value);
        if (value != null) {
            return value;
        } else {
            final List<Driver> driverList = dao.getDrivers(latitude, longitude, radius, limit);
            final Optional<List<Driver>> result = fromNullable(driverList);
            cacheService.add(key, result);
            return result;
        }
    }

    @Override
    public Optional<List<Integer>> addDrivers(final List<Driver> drivers) {
        LOGGER.debug("drivers = {}", drivers);
        return fromNullable(dao.addDrivers(drivers));
    }

    @Override
    public Optional<Integer> updateLocation(final Driver driver, final int id) {
        LOGGER.debug("driver ={}", driver);
        return fromNullable(dao.updateLocation(driver, id));
    }

}
