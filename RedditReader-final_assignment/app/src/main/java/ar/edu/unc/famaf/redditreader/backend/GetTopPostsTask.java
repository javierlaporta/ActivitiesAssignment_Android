package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by javier on 20/10/16.
 */
public class GetTopPostsTask extends AsyncTask<String, Void, List<PostModel>> {

    @Override
    protected List<PostModel> doInBackground(String...subreddit) {
        InputStream input;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL
                    ("https://www.reddit.com/" + subreddit[0] +".json?limit=50").openConnection();
            conn.setRequestMethod("GET");
            input = conn.getInputStream();
            Parser parser = new Parser();
            Listing listing = parser.readJsonStream(input);
            List<PostModel> postList = listing.getChildren();
            return  postList;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException: ","exceptions"+e.getMessage());
            return null;
        }
    }
}