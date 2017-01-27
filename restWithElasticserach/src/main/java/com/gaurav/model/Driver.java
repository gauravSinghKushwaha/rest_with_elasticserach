package com.gaurav.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "driver", type = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 4194330879941521105L;

    @JsonProperty
    @Id
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
    private final Integer id;

    @JsonProperty
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private final String name;

    @JsonProperty
    @JsonUnwrapped
    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed, store = true)
    private final Location location;

    @JsonIgnore
    @GeoPointField
    private GeoPoint geoLoc;

    @SuppressWarnings("unused")
    private Driver() {
        this(null, null, null, null);
    }

    public Driver(final Integer id, final String name, final Location location, final GeoPoint geoLoc) {
        this.id = id;
        this.name = name;
        this.location = location;
        setGeoLoc(location);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Driver other = (Driver) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Driver [id=" + id + ", name=" + name + ", location=" + location + ", geoLoc=" + geoLoc + "]";
    }

    public GeoPoint getGeoLoc() {
        return geoLoc;
    }

    public void setGeoLoc(final Location location) {
        if (location != null && location.getLatitude() != null && location.getLongitude() != null) {
            geoLoc = new GeoPoint(location.getLatitude(), location.getLongitude());
        }
    }
}
