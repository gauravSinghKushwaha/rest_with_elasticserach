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
// @Document(indexName = "driver", type = "driver") nested
public class Location implements Serializable {

    private static final long serialVersionUID = 2298002544814915115L;

    @JsonProperty
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final Double latitude;

    @JsonProperty
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final Double longitude;

    @JsonProperty
    @Field(type = FieldType.Float, index = FieldIndex.not_analyzed, store = true)
    private final Float accuracy;

    @SuppressWarnings("unused")
    private Location() {
        this(null, null, null);
    }

    public Location(final Double latitude, final Double longitude, final Float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Float getAccuracy() {
        return accuracy;
    }

}
