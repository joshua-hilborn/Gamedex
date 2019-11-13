package com.vi.gamedex.igdb;

public class IgdbUtilities {

    public static final String IGDB_API_KEY_HEADER = "user-key";
    public static final String IGDB_BASE_URL = "https://api-v3.igdb.com";
    public static final int IGDB_API_PAGE_LIMIT = 50;

    public static final String IGDB_API_GAMELIST_FIELDS = "fields *, cover.*, artworks.*, screenshots.*, platforms.*, genres.*;";

    public static final String IGDB_ENDPOINT_RELEASE_DATES = "/release_dates";
    public static final String IGDB_ENDPOINT_GAMES = "/games";
}
