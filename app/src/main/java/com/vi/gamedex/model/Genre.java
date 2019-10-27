package com.vi.gamedex.model;

import com.squareup.moshi.Json;

public class Genre {

    @Json(name = "id")
    private int id;
    @Json(name = "created_at")
    private int createdAt;
    @Json(name = "name")
    private String name;
    @Json(name = "slug")
    private String slug;
    @Json(name = "updated_at")
    private int updatedAt;
    @Json(name = "url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

}