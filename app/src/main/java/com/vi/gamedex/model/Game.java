package com.vi.gamedex.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private int id;
    private int category;
    private int createdAt;
    private List<Integer> externalGames = null;
    private String name;
    private double popularity;
    private String slug;
    private int updatedAt;
    private String url;
    private double aggregatedRating;
    private int aggregatedRatingCount;
    private int firstReleaseDate;
    private List<Integer> gameModes = null;
    private List<Integer> genres = null;
    private List<Integer> platforms = null;
    private int pulseCount;
    private double rating;
    private int ratingCount;
    private List<Integer> releaseDates = null;
    private List<Integer> screenshots = null;
    private List<Integer> similarGames = null;
    private String summary;
    private List<Integer> tags = null;
    private List<Integer> themes = null;
    private double totalRating;
    private int totalRatingCount;
    private List<Integer> websites = null;
    private int collection;
    private int cover;
    private List<Integer> involvedCompanies = null;
    private List<Integer> keywords = null;
    private List<Integer> ageRatings = null;
    private int parentGame;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game withId(int id) {
        this.id = id;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Game withCategory(int category) {
        this.category = category;
        return this;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public Game withCreatedAt(int createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Integer> getExternalGames() {
        return externalGames;
    }

    public void setExternalGames(List<Integer> externalGames) {
        this.externalGames = externalGames;
    }

    public Game withExternalGames(List<Integer> externalGames) {
        this.externalGames = externalGames;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game withName(String name) {
        this.name = name;
        return this;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public Game withPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Game withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Game withUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Game withUrl(String url) {
        this.url = url;
        return this;
    }

    public double getAggregatedRating() {
        return aggregatedRating;
    }

    public void setAggregatedRating(double aggregatedRating) {
        this.aggregatedRating = aggregatedRating;
    }

    public Game withAggregatedRating(double aggregatedRating) {
        this.aggregatedRating = aggregatedRating;
        return this;
    }

    public int getAggregatedRatingCount() {
        return aggregatedRatingCount;
    }

    public void setAggregatedRatingCount(int aggregatedRatingCount) {
        this.aggregatedRatingCount = aggregatedRatingCount;
    }

    public Game withAggregatedRatingCount(int aggregatedRatingCount) {
        this.aggregatedRatingCount = aggregatedRatingCount;
        return this;
    }

    public int getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(int firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public Game withFirstReleaseDate(int firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
        return this;
    }

    public List<Integer> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<Integer> gameModes) {
        this.gameModes = gameModes;
    }

    public Game withGameModes(List<Integer> gameModes) {
        this.gameModes = gameModes;
        return this;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public Game withGenres(List<Integer> genres) {
        this.genres = genres;
        return this;
    }

    public List<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
    }

    public Game withPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
        return this;
    }

    public int getPulseCount() {
        return pulseCount;
    }

    public void setPulseCount(int pulseCount) {
        this.pulseCount = pulseCount;
    }

    public Game withPulseCount(int pulseCount) {
        this.pulseCount = pulseCount;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Game withRating(double rating) {
        this.rating = rating;
        return this;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Game withRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public List<Integer> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<Integer> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public Game withReleaseDates(List<Integer> releaseDates) {
        this.releaseDates = releaseDates;
        return this;
    }

    public List<Integer> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Integer> screenshots) {
        this.screenshots = screenshots;
    }

    public Game withScreenshots(List<Integer> screenshots) {
        this.screenshots = screenshots;
        return this;
    }

    public List<Integer> getSimilarGames() {
        return similarGames;
    }

    public void setSimilarGames(List<Integer> similarGames) {
        this.similarGames = similarGames;
    }

    public Game withSimilarGames(List<Integer> similarGames) {
        this.similarGames = similarGames;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Game withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public Game withTags(List<Integer> tags) {
        this.tags = tags;
        return this;
    }

    public List<Integer> getThemes() {
        return themes;
    }

    public void setThemes(List<Integer> themes) {
        this.themes = themes;
    }

    public Game withThemes(List<Integer> themes) {
        this.themes = themes;
        return this;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public Game withTotalRating(double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public int getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(int totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public Game withTotalRatingCount(int totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
        return this;
    }

    public List<Integer> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Integer> websites) {
        this.websites = websites;
    }

    public Game withWebsites(List<Integer> websites) {
        this.websites = websites;
        return this;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public Game withCollection(int collection) {
        this.collection = collection;
        return this;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public Game withCover(int cover) {
        this.cover = cover;
        return this;
    }

    public List<Integer> getInvolvedCompanies() {
        return involvedCompanies;
    }

    public void setInvolvedCompanies(List<Integer> involvedCompanies) {
        this.involvedCompanies = involvedCompanies;
    }

    public Game withInvolvedCompanies(List<Integer> involvedCompanies) {
        this.involvedCompanies = involvedCompanies;
        return this;
    }

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Integer> keywords) {
        this.keywords = keywords;
    }

    public Game withKeywords(List<Integer> keywords) {
        this.keywords = keywords;
        return this;
    }

    public List<Integer> getAgeRatings() {
        return ageRatings;
    }

    public void setAgeRatings(List<Integer> ageRatings) {
        this.ageRatings = ageRatings;
    }

    public Game withAgeRatings(List<Integer> ageRatings) {
        this.ageRatings = ageRatings;
        return this;
    }

    public int getParentGame() {
        return parentGame;
    }

    public void setParentGame(int parentGame) {
        this.parentGame = parentGame;
    }

    public Game withParentGame(int parentGame) {
        this.parentGame = parentGame;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Game withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}