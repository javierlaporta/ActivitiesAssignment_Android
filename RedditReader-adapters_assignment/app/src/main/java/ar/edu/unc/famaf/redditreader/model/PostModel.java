package ar.edu.unc.famaf.redditreader.model;


public class PostModel {
    private String  title;
    private String author;
    private String  date;
    private int comment;
    private int imageResourceId;

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

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
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

    public int getImageResourceId() {
        return imageResourceId;
    }

}