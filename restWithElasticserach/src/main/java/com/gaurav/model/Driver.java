package com.gaurav.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static org.springframework.data.elasticsearch.annotations.FieldIndex.not_analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldType.Integer;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "driversearch", type = "driversearch")
public final class Driver implements Serializable {

    private static final long serialVersionUID = 4194330879941521105L;

    @JsonProperty
    @Id
    @Field(type = Integer, index = not_analyzed, store = true)
    private final Integer id;

    @JsonProperty
    @Field(type = String, index = not_analyzed, store = true)
    private final String name;

    /**
     * TODO , because of some weird spring data issue queries based on this location does not workn hence we use trick
     * like loc string field
     */
    @JsonProperty
    @JsonUnwrapped
    @GeoPointField
    private final Location location;

    /**
     * TODO , because of some weird spring data issue queries based on this location does not workn hence we use trick
     * like loc string field
     */
    @JsonProperty
    @JsonUnwrapped
    @GeoPointField
    private String loc;

    @SuppressWarnings("unused")
    private Driver() {
        this(null, null, null);
    }

    public Driver(final Integer id, final String name, final Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLoc() {
        return loc;
    }

    public void setLoc() {
        if (location != null && location.getLatitude() > 0 && location.getLongitude() > 0) {
            loc = location.getLatitude() + "," + location.getLongitude();
        }
    }

    @Override
    public String toString() {
        return "Driver [id=" + id + ", name=" + name + ", location=" + location + "]";
    }

}
