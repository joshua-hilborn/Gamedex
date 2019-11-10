package com.vi.gamedex.model;

import com.squareup.moshi.Json;

public class ReleaseDate {

    @Json(name = "id")
    private int id;
    @Json(name = "category")
    private int category;
    @Json(name = "created_at")
    private int createdAt;
    @Json(name = "date")
    private int date;
    @Json(name = "game")
    private Game game;
    @Json(name = "human")
    private String human;
    @Json(name = "m")
    private int m;
    @Json(name = "platform")
    private int platform;
    @Json(name = "region")
    private int region;
    @Json(name = "updated_at")
    private int updatedAt;
    @Json(name = "y")
    private int y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getHuman() {
        return human;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}