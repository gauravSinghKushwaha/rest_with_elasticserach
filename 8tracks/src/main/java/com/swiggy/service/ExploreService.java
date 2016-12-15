package com.swiggy.service;

import com.google.common.base.Optional;
import com.swiggy.model.ExploreResult;

public interface ExploreService {
    /**
     * Returns list of playlist and new tags user could search
     * 
     * @param tags
     * @param startRange
     * @param endRange
     * @return
     */
    Optional<ExploreResult> explore(final String tags, final int startRange, final int endRange);

}
