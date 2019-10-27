package com.vi.gamedex.model;

import com.squareup.moshi.Json;

public class Video {

    @Json(name = "id")
    private int id;
    @Json(name = "game")
    private int game;
    @Json(name = "name")
    private String name;
    @Json(name = "video_id")
    private String videoId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}