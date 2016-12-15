package com.swiggy.rest;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.swiggy.perf.PerformanceLabels.*;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.apache.cxf.common.util.CollectionUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;
import com.swiggy.service.PlayListService;
import com.swiggy.util.TagUtil;

/**
 * Rest Implementation
 * 
 * @author gkushwaha
 *
 */
@Service("playListHttpService")
public class PlayListHttpServiceImpl implements PlayListHttpService {

    private final PlayListService service;
    private static final Joiner WHITE_SPACE_JOINER = on(" ").skipNulls();
    private static final Logger LOGGER = getLogger(PlayListHttpServiceImpl.class);

    @Autowired
    public PlayListHttpServiceImpl(final PlayListService playListService) {
        service = checkNotNull(playListService, "The playListService cannot be null");
    }

    @Override
    @Profiled(tag = HTTP_MESSAGE_GET_PLAYLISTS_BY_TAGS, logFailuresSeparately = true)
    public Response getPlayListByTags(final String tags) {
        final List<Tag> tagList = TagUtil.geTagList(tags);
        if (isEmpty(tagList)) {
            return status(Status.BAD_REQUEST).build();
        }
        final Optional<List<PlayList>> playList = service.getPlayListByTags(tagList);
        LOGGER.debug("playList ", playList);
        return Response.ok().entity(playList.get()).build();
    }



    @Override
    @Profiled(tag = HTTP_GET_ALL_PLAYLISTS, logFailuresSeparately = true)
    public Response getAllPlayLists(final int startRange, final int endRange) {
        if (startRange < 0 || endRange < 0 || startRange > endRange || endRange - startRange > 100) {
            return status(Status.BAD_REQUEST).entity(new String("range not correct")).build();
        }

        final Optional<List<PlayList>> playList = service.getAllPlayLists(startRange, endRange == 0 ? 100 : endRange);
        LOGGER.debug("playList ", playList);
        return Response.ok().entity(playList.get()).build();
    }

    @Override
    @Profiled(tag = HTTP_MESSAGE_POST, logFailuresSeparately = true)
    public Response addPlayLists(final List<PlayList> playLists) {
        LOGGER.debug("playList ", playLists);
        final Optional<List<String>> playListIds = service.addPlayLists(playLists);
        LOGGER.debug("playListIds ", playListIds);
        return status(CREATED).entity(WHITE_SPACE_JOINER.join(playListIds.get())).build();
    }

    @Override
    @Profiled(tag = HTTP_PUT_PLAYLIST, logFailuresSeparately = true)
    public Response updatePlayLists(final List<PlayList> playLists) {
        LOGGER.debug("playList ", playLists);
        final Optional<List<String>> updatedIds = service.updatePlayLists(playLists);
        return status(OK).entity(WHITE_SPACE_JOINER.join(updatedIds.get())).build();
    }

    @Override
    @Profiled(tag = HTTP_DELETE_PLAYLIST, logFailuresSeparately = true)
    public Response removePlayListByTags(final List<Tag> tag) {
        LOGGER.debug("tags ", tag);
        service.removePlayListByTags(tag);
        return status(NO_CONTENT).build();
    }

}
