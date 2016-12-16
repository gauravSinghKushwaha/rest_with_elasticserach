package com.swiggy.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayList extends Temp implements Serializable {

    private static final long serialVersionUID = 4194330879941521105L;

    @JsonProperty
    private final String id;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final Long likeCount;
    @JsonProperty
    private final Long playCount;
    @JsonProperty
    private final List<Tag> tags;
    @JsonProperty
    private final Long numberOfSongs;

    @SuppressWarnings("unused")
    private PlayList() {
        this(null, null, null, null, null, null);
    }

    public PlayList(final String id, final String name, final Long numberOfSongs, final Long likeCount,
                    final Long playCount, final List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.likeCount = likeCount;
        this.playCount = playCount;
        this.tags = tags;
        this.numberOfSongs = numberOfSongs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Long getNumberOfSongs() {
        return numberOfSongs;
    }

    @JsonIgnore
    public Long getLikePlayCount() {
        return getLikeCount() + getPlayCount();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
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
        final PlayList other = (PlayList) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlayList [id=" + id + ", name=" + name + ", likeCount=" + likeCount + ", playCount=" + playCount
                + ", tags=" + tags + ", numberOfSongs=" + numberOfSongs + "]";
    }

}
