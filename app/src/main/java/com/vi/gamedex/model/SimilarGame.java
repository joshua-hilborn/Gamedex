// Class not used!

package com.vi.gamedex.model;

import java.util.List;
import com.squareup.moshi.Json;

public class SimilarGame {

    @Json(name = "id")
    private int id;
    @Json(name = "age_ratings")
    private List<Integer> ageRatings = null;
    @Json(name = "aggregated_rating")
    private double aggregatedRating;
    @Json(name = "aggregated_rating_count")
    private int aggregatedRatingCount;
    @Json(name = "category")
    private int category;
    @Json(name = "cover")
    private int cover;
    @Json(name = "created_at")
    private int createdAt;
    @Json(name = "external_games")
    private List<Integer> externalGames = null;
    @Json(name = "first_release_date")
    private int firstReleaseDate;
    @Json(name = "game_engines")
    private List<Integer> gameEngines = null;
    @Json(name = "game_modes")
    private List<Integer> gameModes = null;
    @Json(name = "genres")
    private List<Integer> genres = null;
    @Json(name = "hypes")
    private int hypes;
    @Json(name = "involved_companies")
    private List<Integer> involvedCompanies = null;
    @Json(name = "keywords")
    private List<Integer> keywords = null;
    @Json(name = "name")
    private String name;
    @Json(name = "platforms")
    private List<Integer> platforms = null;
    @Json(name = "player_perspectives")
    private List<Integer> playerPerspectives = null;
    @Json(name = "popularity")
    private double popularity;
    @Json(name = "pulse_count")
    private int pulseCount;
    @Json(name = "rating")
    private double rating;
    @Json(name = "rating_count")
    private int ratingCount;
    @Json(name = "release_dates")
    private List<Integer> releaseDates = null;
    @Json(name = "screenshots")
    private List<Integer> screenshots = null;
    @Json(name = "similar_games")
    private List<Integer> similarGames = null;
    @Json(name = "slug")
    private String slug;
    @Json(name = "summary")
    private String summary;
    @Json(name = "tags")
    private List<Integer> tags = null;
    @Json(name = "themes")
    private List<Integer> themes = null;
    @Json(name = "total_rating")
    private double totalRating;
    @Json(name = "total_rating_count")
    private int totalRatingCount;
    @Json(name = "updated_at")
    private int updatedAt;
    @Json(name = "url")
    private String url;
    @Json(name = "videos")
    private List<Integer> videos = null;
    @Json(name = "websites")
    private List<Integer> websites = null;
    @Json(name = "alternative_names")
    private List<Integer> alternativeNames = null;
    @Json(name = "storyline")
    private String storyline;
    @Json(name = "artworks")
    private List<Integer> artworks = null;
    @Json(name = "multiplayer_modes")
    private List<Integer> multiplayerModes = null;
    @Json(name = "status")
    private int status;
    @Json(name = "collection")
    private int collection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getAgeRatings() {
        return ageRatings;
    }

    public void setAgeRatings(List<Integer> ageRatings) {
        this.ageRatings = ageRatings;
    }

    public double getAggregatedRating() {
        return aggregatedRating;
    }

    public void setAggregatedRating(double aggregatedRating) {
        this.aggregatedRating = aggregatedRating;
    }

    public int getAggregatedRatingCount() {
        return aggregatedRatingCount;
    }

    public void setAggregatedRatingCount(int aggregatedRatingCount) {
        this.aggregatedRatingCount = aggregatedRatingCount;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public List<Integer> getExternalGames() {
        return externalGames;
    }

    public void setExternalGames(List<Integer> externalGames) {
        this.externalGames = externalGames;
    }

    public int getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(int firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public List<Integer> getGameEngines() {
        return gameEngines;
    }

    public void setGameEngines(List<Integer> gameEngines) {
        this.gameEngines = gameEngines;
    }

    public List<Integer> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<Integer> gameModes) {
        this.gameModes = gameModes;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public int getHypes() {
        return hypes;
    }

    public void setHypes(int hypes) {
        this.hypes = hypes;
    }

    public List<Integer> getInvolvedCompanies() {
        return involvedCompanies;
    }

    public void setInvolvedCompanies(List<Integer> involvedCompanies) {
        this.involvedCompanies = involvedCompanies;
    }

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Integer> keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
    }

    public List<Integer> getPlayerPerspectives() {
        return playerPerspectives;
    }

    public void setPlayerPerspectives(List<Integer> playerPerspectives) {
        this.playerPerspectives = playerPerspectives;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getPulseCount() {
        return pulseCount;
    }

    public void setPulseCount(int pulseCount) {
        this.pulseCount = pulseCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<Integer> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<Integer> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<Integer> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Integer> screenshots) {
        this.screenshots = screenshots;
    }

    public List<Integer> getSimilarGames() {
        return similarGames;
    }

    public void setSimilarGames(List<Integer> similarGames) {
        this.similarGames = similarGames;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public List<Integer> getThemes() {
        return themes;
    }

    public void setThemes(List<Integer> themes) {
        this.themes = themes;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public int getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(int totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getVideos() {
        return videos;
    }

    public void setVideos(List<Integer> videos) {
        this.videos = videos;
    }

    public List<Integer> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Integer> websites) {
        this.websites = websites;
    }

    public List<Integer> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<Integer> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public List<Integer> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Integer> artworks) {
        this.artworks = artworks;
    }

    public List<Integer> getMultiplayerModes() {
        return multiplayerModes;
    }

    public void setMultiplayerModes(List<Integer> multiplayerModes) {
        this.multiplayerModes = multiplayerModes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

}