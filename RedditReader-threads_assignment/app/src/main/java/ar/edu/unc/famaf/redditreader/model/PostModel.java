package ar.edu.unc.famaf.redditreader.model;


import java.net.MalformedURLException;
import java.net.URL;

public class PostModel {
    private String  title;
    private String author;
    private String  date;
    private int comment;
    private String imageResourceUrl;

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

    public void setComment(int comment) {
        this.comment = comment;
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

    public int getComment() {
        return comment;
    }

}