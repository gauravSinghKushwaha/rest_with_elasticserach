package com.gaurav.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.gaurav.model.Driver;

/**
 * Rest interface for driver service
 *
 * @author gkushwaha
 *
 */
public interface DriverHttpService {

    /**
     *
     * returns drivers in radius from current latitude , longitude
     *
     * @param latitude
     * @param longitude
     * @param radius
     *            optional, default to 500
     * @param limit
     *            optional, default to 10
     * @return
     */
    @GET
    @Produces(APPLICATION_JSON)
    @Path("/drivers")
    Response getDrivers(@QueryParam("latitude") final float latitude, @QueryParam("longitude") float longitude,
                        @QueryParam("radius") int radius, @QueryParam("limit") final int limit);

    /**
     * Add drivers to system
     *
     * @param drivers
     * @return
     */
    @POST
    @Produces(TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    @Path("/drivers")
    Response addDrivers(final List<Driver> drivers);

    /**
     * Update location of the driver
     */
    @PUT
    @Produces(TEXT_PLAIN)
    @Consumes(APPLICATION_JSON)
    @Path("/driver/{id}/location")
    Response updateLocation(final Driver driver, @PathParam("id") int id);

}
