package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() {
        PostModel post1 = new PostModel();
        post1.setTitle("Google Pixel XL 128GB Smartphone Goes Out of Stock");
        post1.setAuthor("/r/Android ");
        post1.setDate("Hace 14 hs");
        post1.setComment(294);
        post1.setImageResourceId(1);

        PostModel post2 = new PostModel();




        return null;
    }
}
