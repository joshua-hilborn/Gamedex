package com.vi.gamedex.igdb;

public class IgdbUtilities {

    /*
    Image Sizes
cover_small	    90 x 128	Fit
screenshot_med	569 x 320	Lfill, Center gravity
cover_big	    264 x 374	Fit
logo_med	    284 x 160	Fit
screenshot_big	889 x 500	Lfill, Center gravity
screenshot_huge	1280 x 720	Lfill, Center gravity
thumb	        90 x 90	    Thumb, Center gravity
micro	        35 x 35	    Thumb, Center gravity
720p	        1280 x 720	Fit, Center gravity
1080p	        1920 x 1080	Fit, Center gravity
     */

    public static final String IGDB_API_KEY_HEADER = "user-key";
    public static final String IGDB_BASE_URL = "https://api-v3.igdb.com";
    public static final int IGDB_API_PAGE_LIMIT = 50;

    public static final String IGDB_API_GAMELIST_FIELDS = "fields *, cover.*, artworks.*, screenshots.*, platforms.*, genres.*;";

    public static final String IGDB_ENDPOINT_RELEASE_DATES = "/release_dates";
    public static final String IGDB_ENDPOINT_GAMES = "/games";

    public static final String IGDB_IMAGE_BASE_URL = "https://images.igdb.com/igdb/image/upload/t_";
    public static final String IGDB_IMAGE_FORMAT_JPG = ".jpg";

    public static final String IGDB_IMAGE_SIZE_90x128 = "cover_small/";
    public static final String IGDB_IMAGE_SIZE_569x320 = "screenshot_med/";
    public static final String IGDB_IMAGE_SIZE_264x374 = "cover_big/";
    public static final String IGDB_IMAGE_SIZE_284x160 = "logo_med/";
    public static final String IGDB_IMAGE_SIZE_889x500 = "screenshot_big/";
    public static final String IGDB_IMAGE_SIZE_1280x720 = "screenshot_huge/";
    public static final String IGDB_IMAGE_SIZE_90x90 = "thumb/";
    public static final String IGDB_IMAGE_SIZE_35x35 = "micro/";
    public static final String IGDB_IMAGE_SIZE_1280x720p = "720p/";
    public static final String IGDB_IMAGE_SIZE_1920x1080 = "1080p/";
}
