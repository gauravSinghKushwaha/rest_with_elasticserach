package com.gaurav.validate;

import org.springframework.stereotype.Component;

import com.gaurav.model.Location;

@Component
public class ValidateImpl implements Validate {

    float MAX_LATITUDE = 90;
    float MIN_LATITUDE = -90;
    float MAX_LONGITUDE = 180;
    float MIN_LONGITUDE = -180;
    int DEFAULT_LIMIT = 10;
    int DEFAULT_RADIUS = 500;

    @Override
    public Integer getRadius(final Integer radius) {
        return radius == null || radius <= 0 ? DEFAULT_RADIUS : radius;
    }

    @Override
    public Integer getLimit(final Integer limit) {
        return limit == null || limit <= 0 ? DEFAULT_RADIUS : limit;
    }

    @Override
    public boolean validateId(final Integer id) {
        return id != null && id > 0 && id <= 50000;
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
    public boolean validateLatitude(final Float latitude) {
        return latitude > MIN_LATITUDE && latitude < MAX_LATITUDE;
    }

    @Override
    public boolean validateLongitude(final Float longitude) {
        return longitude > MIN_LONGITUDE && longitude < MAX_LONGITUDE;
    }

}
