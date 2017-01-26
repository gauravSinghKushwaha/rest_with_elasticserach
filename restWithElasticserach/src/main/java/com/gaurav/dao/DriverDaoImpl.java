package com.gaurav.dao;

import static com.gaurav.perf.PerformanceLabels.DAO_ADD_DRIVER;
import static com.gaurav.perf.PerformanceLabels.DAO_GET_DRIVER;
import static com.gaurav.perf.PerformanceLabels.DAO_UPDATE_DRIVER_LOC;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gaurav.elasticsearch.repo.DriverIndexRepo;
import com.gaurav.model.Driver;

@Service("driverDao")
public class DriverDaoImpl implements DriverDao {

    private static final Logger LOG = getLogger(DriverDaoImpl.class);
    private static final int FIRST = 0;
    private final DriverIndexRepo indexRepo;

    @Autowired
    public DriverDaoImpl(final DriverIndexRepo indexRepo) {
        this.indexRepo = checkNotNull(indexRepo);
    }

    @Profiled(tag = DAO_GET_DRIVER, logFailuresSeparately = true)
    @Override
    public List<Driver> getDrivers(final float latitude, final float longitude, final int radius, final int limit) {
        final Iterable<Driver> res = indexRepo.search(QueryBuilders.matchAllQuery(), new PageRequest(FIRST, limit));
        res.forEach(e -> LOG.debug("playList =", e.toString()));
        return newArrayList(res);
    }

    @Profiled(tag = DAO_ADD_DRIVER, logFailuresSeparately = true)
    @Override
    public List<Integer> addDrivers(final List<Driver> drivers) {
        final Iterable<Driver> result = indexRepo.save(drivers);
        result.forEach(e -> LOG.debug("driver={}", e));
        final List<Integer> ids = newArrayList();
        result.forEach(t -> ids.add(t.getId()));
        return ids;
    }

    @Profiled(tag = DAO_UPDATE_DRIVER_LOC, logFailuresSeparately = true)
    @Override
    public Integer updateLocation(final Driver driver, final int id) {
        final Driver existing = indexRepo.findOne(id);
        if (existing != null) {
            final Driver result =
                    indexRepo.save(new Driver(existing.getId(), existing.getName(), driver.getLocation()));
            return result != null ? id : null;
        }
        LOG.debug("No driver with id = {}", id);
        return -1;
    }

}