package com.vi.gamedex.model;

import java.util.List;
import com.squareup.moshi.Json;

public class Platform {

    @Json(name = "id")
    private int id;
    @Json(name = "abbreviation")
    private String abbreviation;
    @Json(name = "alternative_name")
    private String alternativeName;
    @Json(name = "category")
    private int category;
    @Json(name = "created_at")
    private int createdAt;
    @Json(name = "name")
    private String name;
    @Json(name = "platform_logo")
    private int platformLogo;
    @Json(name = "slug")
    private String slug;
    @Json(name = "updated_at")
    private int updatedAt;
    @Json(name = "url")
    private String url;
    @Json(name = "versions")
    private List<Integer> versions = null;
    @Json(name = "websites")
    private List<Integer> websites = null;
    @Json(name = "generation")
    private int generation;
    @Json(name = "product_family")
    private int productFamily;
    @Json(name = "summary")
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlatformLogo() {
        return platformLogo;
    }

    public void setPlatformLogo(int platformLogo) {
        this.platformLogo = platformLogo;
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

    public List<Integer> getVersions() {
        return versions;
    }

    public void setVersions(List<Integer> versions) {
        this.versions = versions;
    }

    public List<Integer> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Integer> websites) {
        this.websites = websites;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(int productFamily) {
        this.productFamily = productFamily;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}