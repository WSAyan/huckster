
package com.wsayan.hackster.core.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Source {
    @SerializedName("category")
    private String category;
    @SerializedName("country")
    private String country;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private String id;
    @SerializedName("language")
    private String language;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
