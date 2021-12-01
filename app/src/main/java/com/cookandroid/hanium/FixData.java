package com.cookandroid.hanium;

import com.google.gson.annotations.SerializedName;

class FixData {
    @SerializedName("id")
    private String id;

    @SerializedName("organization")
    private String organization;

    @SerializedName("date")
    private String date;

    @SerializedName("content")
    private String content;

    public FixData(String id, String organization, String content, String date) {
        this.id = id;
        this.organization = organization;
        this.date = date;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
