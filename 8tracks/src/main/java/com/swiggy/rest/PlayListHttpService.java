package com.swiggy.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.swiggy.model.PlayList;
import com.swiggy.model.Tag;

/**
 * Rest interface for playslists
 * 
 * @author gkushwaha
 *
 */
public interface PlayListHttpService {

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/playlists/tags")
    Response getPlayListByTags(@QueryParam(value = "q") final String tags);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/playlists")
    Response getAllPlayLists(@QueryParam("startrange") final int startRange, @QueryParam("endrange") final int endRange);

    @POST
    @Produces(TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    @Path("/playlists")
    Response addPlayLists(final List<PlayList> playLists);

    @PUT
    @Produces(TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    @Path("/playlists")
    Response updatePlayLists(final List<PlayList> playLists);

    @DELETE
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("/playlists/tags")
    Response removePlayListByTags(final List<Tag> tag);

}
