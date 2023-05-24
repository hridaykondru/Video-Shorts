package com.example.hriday_kondru_assignment;

public class Video {
    private String id;
    private String title;
    private String thumbnailUrl;
    private String mediaUrl;
    private String description;
    public Video(String id, String title, String thumbnailUrl, String mediaUrl, String description) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.mediaUrl = mediaUrl;
        this.description = description;
    }
    public String getThumbnailUrl() {

        return this.thumbnailUrl;
    }
    public String getVideoUrl() {
        return this.mediaUrl;
    }
    public String getVideoText() {

        return this.title;
    }
    public String getVideoDescription() {

        return this.description;
    }
}