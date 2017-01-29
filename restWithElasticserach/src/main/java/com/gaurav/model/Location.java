package com.gaurav.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Location implements Serializable {

    private static final long serialVersionUID = 1727234265919306052L;

    @JsonProperty("latitude")
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final double latitude;

    @JsonProperty("longitude")
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final double longitude;

    @JsonProperty
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final Float accuracy;

    @SuppressWarnings("unused")
    private Location() {
        this(0, 0, null);
    }

    public Location(final double latitude, final double longitude, final Float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    @Override
    public String toString() {
        return "Location [lat=" + latitude + ", lon=" + longitude + "]";
    }

}
