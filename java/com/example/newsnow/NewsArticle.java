package com.example.newsnow;

public class NewsArticle {
    private final String title;
    private final String source;
    private final String date;
    private final String summary;
    private final String url;
    private final String category;
    private float userRating;

    public NewsArticle(String title, String source, String date, String summary, String url, String category) {
        this.title = title;
        this.source = source;
        this.date = date;
        this.summary = summary;
        this.url = url;
        this.category = category;
        this.userRating = 0f;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
