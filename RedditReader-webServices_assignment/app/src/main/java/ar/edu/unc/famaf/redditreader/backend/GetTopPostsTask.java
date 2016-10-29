package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by javier on 20/10/16.
 */
public class GetTopPostsTask extends AsyncTask<Void, Void, List<PostModel>> {

    @Override
    protected List<PostModel> doInBackground(Void...params) {
        InputStream input = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL
                    ("https://www.reddit.com/top.json?limit=50").openConnection();
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