package com.vi.gamedex.igdb;

public class IgdbUtilities {

    String body = "fields *, game.*, game.cover.*, game.artworks.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.videos.*;" +
            "where date > " +  ";" +
            "sort date asc;" +
            "limit 50;";
    public static final String IGDB_API_KEY_HEADER = "user-key";
    public static final String IGDB_BASE_URL = "https://api-v3.igdb.com";


    public static final String IGDB_ENDPOINT_RELEASE_DATES = "/release_dates";
    public static final String IGDB_ENDPOINT_GAMES = "/games";
}
