package ar.edu.unc.famaf.redditreader.backend;

import java.net.URL;
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
        PostModel post2 = new PostModel();
        PostModel post3 = new PostModel();
        PostModel post4 = new PostModel();
        PostModel post5 = new PostModel();

        PostModel[] postArray = {post1,post2,post3,post4,post5};
        String[] titleArray = {"Google Pixel XL 128GB Smartphone Goes Out of Stock",
                "A simple tips from me about android performance",
                "Crash when editing a comment that is still in progress",
                "iOS 10, Outlook & Messenger app tones changed",
                "The best games to play on your GBA emulator!"};
        String[] authorArray = {"/r/Android","/r/DevAndriod","/r/JoshuaTheFox","/r/ios","/r/GBA4iOS"};
        String[] dateArray = {"Hace 14 hs","Hace 12 hs","Hace 15 hs","Hace 11 hs","Hace 10 hs"};
        Integer[] commentArray = {294,352,152,134,234};
        String url = "https://www.google.com.ar/search?q=google+pixel&espv=2&biw=1242&bih=593&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjo5MH409XPAhVLi5AKHYOMAZUQ_AUIBigB#q=google&tbs=isz:i&tbm=isch&imgrc=CAN2a9julVsy3M%3A";

        //Integer[] imageArray = {R.drawable.post1,R.drawable.developer,R.drawable.fox,
        // R.drawable.ios,R.drawable.ios_reddit};

        List<PostModel> postLst = new ArrayList<PostModel>();
        for (int i=0; i < 5 ; i++){
            postArray[i].setTitle(titleArray[i]);
            postArray[i].setAuthor(authorArray[i]);
            postArray[i].setDate(dateArray[i]);
            postArray[i].setComment(commentArray[i]);
            postArray[i].setimageResourceUrl(url);
            //postArray[i].setimageResourceId(imageArray[i]);
            postLst.add(postArray[i]);
        }
        return postLst;
    }
}


