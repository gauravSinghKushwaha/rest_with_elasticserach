package com.gaurav.validate;

import static com.google.common.base.Preconditions.checkArgument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gaurav.model.Location;

@Component
public class ValidateImpl implements Validate {

    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private final double defaultRadius;
    private final int defaultLimit;

    @Autowired
    public ValidateImpl(@Value("${default.radius}") final double defaultRadius,
                        @Value("${default.page.size}") final int defaultLimit) {
        checkArgument(defaultRadius > 0);
        checkArgument(defaultLimit > 0);
        this.defaultRadius = defaultRadius;
        this.defaultLimit = defaultLimit;
    }

    @Override
    public double getRadius(final double radius) {
        return radius <= 0 ? defaultRadius : radius;
    }

    @Override
    public int getLimit(final int limit) {
        return limit <= 0 ? defaultLimit : limit;
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
