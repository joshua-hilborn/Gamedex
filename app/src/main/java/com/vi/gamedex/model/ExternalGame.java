package com.vi.gamedex.model;

import com.squareup.moshi.Json;

public class ExternalGame {

    @Json(name = "id")
    private int id;
    @Json(name = "category")
    private int category;
    @Json(name = "created_at")
    private int createdAt;
    @Json(name = "game")
    private int game;
    @Json(name = "name")
    private String name;
    @Json(name = "uid")
    private String uid;
    @Json(name = "updated_at")
    private int updatedAt;
    @Json(name = "url")
    private String url;
    @Json(name = "year")
    private int year;

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

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
