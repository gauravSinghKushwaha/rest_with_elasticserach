package com.swiggy.rest;

import static com.swiggy.perf.PerformanceLabels.HTTP_GET_EXPLORE;
import static com.swiggy.util.TagUtil.geTagList;
import static javax.ws.rs.core.Response.status;
import static org.apache.cxf.common.util.CollectionUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.swiggy.model.ExploreResult;
import com.swiggy.model.Tag;
import com.swiggy.service.ExploreService;

@Service("exploreHttpService")
public class ExploreHttpServiceImpl implements ExploreHttpService {

    private static final Logger LOGGER = getLogger(ExploreHttpServiceImpl.class);

    private final ExploreService service;

    @Autowired
    public ExploreHttpServiceImpl(final ExploreService service) {
        this.service = service;
    }

    @Override
    @Profiled(tag = HTTP_GET_EXPLORE, logFailuresSeparately = true)
    public Response explore(final String tags, final int startRange, final int endRange) {
        LOGGER.debug("tags={} , startRange = {} , endRange = {}", tags, startRange, endRange);

        if (startRange < 0 || endRange < 0 || startRange > endRange || endRange - startRange > 100) {
            return status(Status.BAD_REQUEST).entity(new String("range not correct")).build();
        }

        final List<Tag> tagList = geTagList(tags);
        if (isEmpty(tagList)) {
            return status(Status.BAD_REQUEST).build();
        }

        final Optional<ExploreResult> result = service.explore(tags, startRange, endRange);
        LOGGER.debug("ExploreResult ", result);
        return Response.ok().entity(result.get()).build();
    }
}
