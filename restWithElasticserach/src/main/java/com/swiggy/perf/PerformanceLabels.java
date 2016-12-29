package com.swiggy.perf;

/**
 * The performance labels for Offline-Storage.
 * 
 * @author rohanarya
 * 
 */
public final class PerformanceLabels {

    private static final String HTTP = "http.";
    private static final String GET = ".get";
    private static final String POST = ".post";
    private static final String DELETE = ".delete";

    private static final String EXPLORE = "explore";
    private static final String PLAYLIST = "playList";

    public static final String HTTP_MESSAGE_POST = HTTP + PLAYLIST + POST;
    public static final String HTTP_MESSAGE_GET_PLAYLISTS_BY_TAGS = HTTP + PLAYLIST + GET;
    public static final String HTTP_GET_ALL_PLAYLISTS = HTTP + PLAYLIST + GET + "ALL";
    public static final String HTTP_DELETE_PLAYLIST = HTTP + PLAYLIST + DELETE;
    public static final String HTTP_PUT_PLAYLIST = HTTP + PLAYLIST + DELETE;

    public static final String HTTP_GET_EXPLORE = HTTP + EXPLORE + GET;


}
