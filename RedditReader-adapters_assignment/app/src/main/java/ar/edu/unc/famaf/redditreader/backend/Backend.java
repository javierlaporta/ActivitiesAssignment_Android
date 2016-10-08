package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unc.famaf.redditreader.R;

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
        post1.setAuthor("/r/Android");
        post1.setDate("Hace 14 hs");
        post1.setComment(294);
        post1.setImageResourceId(R.drawable.post1);

        PostModel post2 = new PostModel();
        post2.setTitle("A simple tips from me about android performance");
        post2.setAuthor("/r/DevAndriod");
        post2.setDate("Hace 12 hs");
        post2.setComment(352);
        post2.setImageResourceId(R.drawable.developer);

        PostModel post3 = new PostModel();
        post3.setTitle("Crash when editing a comment that is still in progress");
        post3.setAuthor("/r/JoshuaTheFox");
        post3.setDate("Hace 15 hs");
        post3.setComment(152);
        post3.setImageResourceId(R.drawable.fox);

        PostModel post4 = new PostModel();
        post4.setTitle("iOS 10, Outlook & Messenger app tones changed");
        post4.setAuthor("/r/ios");
        post4.setDate("Hace 11 hs");
        post4.setComment(134);
        post4.setImageResourceId(R.drawable.ios);

        PostModel post5 = new PostModel();
        post5.setTitle("The best games to play on your GBA emulator!");
        post5.setAuthor("/r/GBA4iOS");
        post5.setDate("Hace 10 hs");
        post5.setComment(234);
        post5.setImageResourceId(R.drawable.ios_reddit);

        //manera poco elegante
        List<PostModel> postLst = new ArrayList<PostModel>();
        postLst.add(post1);
        postLst.add(post2);
        postLst.add(post3);
        postLst.add(post4);
        postLst.add(post5);

        return postLst;
    }
}


