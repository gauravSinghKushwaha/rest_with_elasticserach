package com.swiggy.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExploreResult implements Serializable {
    private static final long serialVersionUID = -3614135065436574619L;

    @JsonProperty
    private final List<PlayList> playLists;

    @JsonProperty
    private final List<Tag> suggestions;

    @SuppressWarnings("unused")
    private ExploreResult() {
        this(null, null);
    }

    public ExploreResult(final List<PlayList> playLists, final List<Tag> suggestions) {
        this.playLists = playLists;
        this.suggestions = suggestions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (playLists == null ? 0 : playLists.hashCode());
        result = prime * result + (suggestions == null ? 0 : suggestions.hashCode());
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
        final ExploreResult other = (ExploreResult) obj;
        if (playLists == null) {
            if (other.playLists != null) {
                return false;
            }
        } else if (!playLists.equals(other.playLists)) {
            return false;
        }
        if (suggestions == null) {
            if (other.suggestions != null) {
                return false;
            }
        } else if (!suggestions.equals(other.suggestions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExploreResult [playLists=" + playLists + ", suggestions=" + suggestions + "]";
    }

}
