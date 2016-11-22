package ar.edu.unc.famaf.redditreader.model;

import java.io.Serializable;

public class PostModel implements Serializable {
    private String  title;
    private String author;
    private String  date;
    private String comment;
    private String imageResourceUrl;
    private String subreddit;
    private String linkWeb;

    public void setimageResourceUrl(String imageResourceUrl){
        this.imageResourceUrl = imageResourceUrl;
    }

    public String getimageResourceUrl(){
        return imageResourceUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getLinkWeb() {
        return linkWeb;
    }
}