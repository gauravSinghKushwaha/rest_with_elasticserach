package com.swiggy.util;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.List;

import com.swiggy.model.Tag;

public final class TagUtil {
    /**
     * Converting space separated tags to TAG objec list
     * 
     * @param tags
     * @return
     */
    public static final List<Tag> geTagList(final String tags) {
        if (null == tags || tags.length() <= 0) {
            return null;
        }
        final List<Tag> tagList = newArrayList();
        final String[] tagArray = tags.split(" ");
        asList(tagArray).forEach(t -> {
            tagList.add(new Tag(t));
        });
        return tagList;
    }
}
