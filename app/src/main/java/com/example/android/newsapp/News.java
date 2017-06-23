package com.example.android.newsapp;

public class News {

    /** Title of the news */
    String title;
    /** Author of the news */
    String author;
    /** Url of the news */
    String url;
    /** Date of the news */
    String date;
    /** Category of the news */
    String category;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title is the title of the news
     * @param author is the author of the news
     * @param url is the http address of the news
     * @param date is the date when the newss was published
     * @param category is the main news section in which the news appear
     */
    public News(String title, String author, String url, String date, String category) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.date = date;
        this.category = category;
    }

    /**
     * Getters and Setters.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
