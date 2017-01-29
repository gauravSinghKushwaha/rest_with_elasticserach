package com.gaurav.validate;

import com.gaurav.model.Location;

public interface Validate {

    boolean validateId(final int id);

    boolean validateLatitude(final Location location);

    boolean validateLatitude(final double latitude);

    boolean validateLongitude(final Location location);

    boolean validateLongitude(final double longitude);

    double getRadius(final double radius);

    int getLimit(final int limit);
}
