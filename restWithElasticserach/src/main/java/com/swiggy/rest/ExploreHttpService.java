package com.swiggy.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface ExploreHttpService {

    /**
     * example URL http://localhost:9090/8tracks/resources/rest/explore?q=indie+pop+latina&startrange=0&endrange=100
     * 
     * @param tags
     *            space separated tags
     * @param startRange
     *            start range
     * @param endRange
     *            end range
     * @return
     */
    @GET
    @Produces(APPLICATION_JSON)
    @Path("/playlist")
    Response explore(@QueryParam(value = "q") final String tags, @QueryParam("startrange") final int startRange,
                     @QueryParam("endrange") final int endRange);

}
