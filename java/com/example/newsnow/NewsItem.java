package com.example.newsnow;

public class NewsItem {
    private String title;
    private String snippet;
    private String url;
    private String imageUrl;
    private float rating;
    private String category;
    private String source;
    private String time;

    public NewsItem(String title, String snippet, String url, String imageUrl, float rating, String category, String source, String time) {
        this.title = title;
        this.snippet = snippet;
        this.url = url;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.category = category;
        this.source = source;
        this.time = time;
    }

    public String getTitle() { return title; }
    public String getSnippet() { return snippet; }
    public String getUrl() { return url; }
    public String getImageUrl() { return imageUrl; }
    public float getRating() { return rating; }
    public String getCategory() { return category; }
    public String getSource() { return source; }
    public String getTime() { return time; }
}
