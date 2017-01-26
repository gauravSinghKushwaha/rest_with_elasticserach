package com.gaurav.validate;

import com.gaurav.model.Location;

public interface Validate {
    boolean validateId(Integer id);

    boolean validateLatitude(final Location location);

    boolean validateLatitude(final Float latitude);

    boolean validateLongitude(final Location location);

    boolean validateLongitude(final Float longitude);

    Integer getRadius(final Integer radius);

    Integer getLimit(final Integer limit);
}
