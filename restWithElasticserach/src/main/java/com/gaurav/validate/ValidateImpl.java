package com.gaurav.validate;

import org.springframework.stereotype.Component;

import com.gaurav.model.Location;

@Component
public class ValidateImpl implements Validate {

    double MAX_LATITUDE = 90;
    double MIN_LATITUDE = -90;
    double MAX_LONGITUDE = 180;
    double MIN_LONGITUDE = -180;
    double DEFAULT_RADIUS = 500d;
    int DEFAULT_LIMIT = 10;

    @Override
    public double getRadius(final double radius) {
        return radius <= 0 ? DEFAULT_RADIUS : radius;
    }

    @Override
    public int getLimit(final int limit) {
        return limit <= 0 ? DEFAULT_LIMIT : limit;
    }

    @Override
    public boolean validateId(final int id) {
        return id > 0 && id <= 50000;
    }

    @Override
    public boolean validateLatitude(final Location location) {
        return validateLatitude(location.getLatitude());
    }

    @Override
    public boolean validateLongitude(final Location location) {
        return validateLongitude(location.getLongitude());
    }

    @Override
    public boolean validateLatitude(final double latitude) {
        return latitude > MIN_LATITUDE && latitude < MAX_LATITUDE;
    }

    @Override
    public boolean validateLongitude(final double longitude) {
        return longitude > MIN_LONGITUDE && longitude < MAX_LONGITUDE;
    }

}
