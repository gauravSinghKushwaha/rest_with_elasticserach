package com.gaurav.perf;

/**
 * The performance labels for Offline-Storage.
 *
 * @author gauravSingh
 *
 */
public final class PerformanceLabels {

    private static final String DAO = "dao.";
    private static final String HTTP = "http.";
    private static final String GET = ".get";
    private static final String POST = ".post";
    private static final String DELETE = ".delete";

    private static final String DRIVER = "driver";

    public static final String HTTP_POST_ADD_DRIVER = HTTP + DRIVER + POST;
    public static final String HTTP_GET_DRIVERS = HTTP + DRIVER + GET;
    public static final String HTTP_PUT_DRIVER_LOC = HTTP + DRIVER + DELETE;
    public static final String HTTP_GET_ALL_DRIVERS = HTTP + DRIVER + GET + "ALL";

    public static final String DAO_ADD_DRIVER = DAO + DRIVER + POST;
    public static final String DAO_GET_DRIVER = DAO + DRIVER + GET;
    public static final String DAO_UPDATE_DRIVER_LOC = DAO + DRIVER + DELETE;
    public static final String DAO_GET_ALL_DRIVERS = DAO + DRIVER + GET + "ALL";

}
