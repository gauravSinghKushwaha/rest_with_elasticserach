package com.swiggy.model;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(isGetterVisibility = NONE)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Tag implements Serializable {
    private static final long serialVersionUID = 5723220982958862968L;

    @SuppressWarnings("unused")
    private Tag() {
        this(null);
    }

    @JsonProperty
    private final String tag;

    public String getTag() {
        return tag;
    }

    public Tag(final String tag) {
        super();
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (tag == null ? 0 : tag.hashCode());
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
        final Tag other = (Tag) obj;
        if (tag == null) {
            if (other.tag != null) {
                return false;
            }
        } else if (!tag.equals(other.tag)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag [tag=" + tag + "]";
    }

}
