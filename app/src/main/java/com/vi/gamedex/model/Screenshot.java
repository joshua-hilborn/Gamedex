package com.vi.gamedex.model;

import com.squareup.moshi.Json;

public class Screenshot {

    @Json(name = "id")
    private int id;
    @Json(name = "game")
    private int game;
    @Json(name = "height")
    private int height;
    @Json(name = "image_id")
    private String imageId;
    @Json(name = "url")
    private String url;
    @Json(name = "width")
    private int width;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}