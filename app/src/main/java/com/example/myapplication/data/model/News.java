package com.example.myapplication.data.model;

public class News {
    private int IdNews;
    private String Title;
    private String Description;
    private String Author;
    private String Picture;
    private String PubDate;
    private String Guid;
    private String Link;

    public String getPubDate() {
        return PubDate;
    }

    public void setPubDate(String pubDate) {
        PubDate = pubDate;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public News(String title, String description, String picture, String pubDate, String guid, String link) {
        Title = title;
        Description = description;
        Picture = picture;
        PubDate = pubDate;
        Guid = guid;
        Link = link;
    }

    public void setIdNews(int idNews) {
        IdNews = idNews;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public int getIdNews() {
        return IdNews;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getAuthor() {
        return Author;
    }

    public String getPicture() {
        return Picture;
    }

    public News(int idNews, String title, String description, String author, String picture) {
        IdNews = idNews;
        Title = title;
        Description = description;
        Author = author;
        Picture = picture;
    }
    public News(int idNews, String title, String description, String author, String picture,String date) {
        IdNews = idNews;
        Title = title;
        Description = description;
        Author = author;
        Picture = picture;
        PubDate = date;
    }
}
