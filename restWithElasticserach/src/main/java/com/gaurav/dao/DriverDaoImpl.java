package com.gaurav.dao;

import static com.gaurav.perf.PerformanceLabels.DAO_ADD_DRIVER;
import static com.gaurav.perf.PerformanceLabels.DAO_GET_ALL_DRIVERS;
import static com.gaurav.perf.PerformanceLabels.DAO_GET_DRIVER;
import static com.gaurav.perf.PerformanceLabels.DAO_UPDATE_DRIVER_LOC;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static org.elasticsearch.common.geo.GeoDistance.ARC;
import static org.elasticsearch.common.unit.DistanceUnit.fromString;
import static org.elasticsearch.index.query.QueryBuilders.geoDistanceQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.sort.SortBuilders.geoDistanceSort;
import static org.elasticsearch.search.sort.SortOrder.ASC;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.gaurav.elasticsearch.repo.DriverIndexRepo;
import com.gaurav.model.Driver;

@Service("driverDao")
public class DriverDaoImpl implements DriverDao {

    private static final Logger LOG = getLogger(DriverDaoImpl.class);
    private static final int FIRST = 0;
    private static final String LOC_FIELD = "loc";
    private final String metric;
    private final DriverIndexRepo indexRepo;

    @Autowired
    public DriverDaoImpl(final DriverIndexRepo indexRepo, @Value("${radius.metric}") final String metric) {
        this.metric = checkNotNull(metric);
        this.indexRepo = checkNotNull(indexRepo);
    }

    @Profiled(tag = DAO_GET_DRIVER, logFailuresSeparately = true)
    @Override
    public List<Driver> getDrivers(final double latitude, final double longitude, final double radius, final int limit) {

        final GeoDistanceQueryBuilder filter =
                geoDistanceQuery(LOC_FIELD).distance(radius, fromString(metric)).point(latitude, longitude)
                .geoDistance(ARC);

        final GeoDistanceSortBuilder sortBy =
                geoDistanceSort(LOC_FIELD).point(latitude, longitude).geoDistance(ARC).order(ASC);

        final PageRequest pageRequest = new PageRequest(FIRST, limit);

        final SearchQuery searchQuery =
                new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withFilter(filter).withSort(sortBy)
                .withPageable(pageRequest).build();

        final Iterable<Driver> res = indexRepo.search(searchQuery);

        debug(res);
        return newArrayList(res);
    }

    private void debug(final Iterable<Driver> res) {
        if (LOG.isDebugEnabled()) {
            res.forEach(t -> LOG.debug("driver ={}", t));
        }
    }

    @Profiled(tag = DAO_GET_ALL_DRIVERS, logFailuresSeparately = true)
    @Override
    public List<Driver> getAllDrivers(final int limit) {
        final PageRequest pageRequest = new PageRequest(FIRST, limit);
        final SearchQuery searchQuery =
                new NativeSearchQueryBuilder().withQuery(matchAllQuery()).withPageable(pageRequest).build();

        final Iterable<Driver> res = indexRepo.search(searchQuery);

        debug(res);
        return newArrayList(res);
    }

    @Profiled(tag = DAO_ADD_DRIVER, logFailuresSeparately = true)
    @Override
    public List<Integer> addDrivers(final List<Driver> drivers) {
        drivers.forEach(t -> t.setLoc());
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
            final Driver entity = new Driver(existing.getId(), existing.getName(), driver.getLocation());
            entity.setLoc();
            final Driver result = indexRepo.save(entity);
            LOG.debug("Driver = {}", driver);
            return result != null ? id : null;
        }
        LOG.debug("No driver with id = {}", id);
        return -1;
    }

}
