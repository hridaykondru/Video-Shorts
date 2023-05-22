package com.example.hriday_kondru_assignment;

public class Video {
    private String id;
    private String title;
    private String thumbnailUrl;
    private String mediaUrl;
    public Video(String id, String title, String thumbnailUrl, String mediaUrl) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.mediaUrl = mediaUrl;
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
    // Getters and setters (if needed)

    // Other methods and fields specific to your Video class
}