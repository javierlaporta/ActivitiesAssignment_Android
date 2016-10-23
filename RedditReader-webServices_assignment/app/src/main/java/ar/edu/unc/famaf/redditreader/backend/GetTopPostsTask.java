package ar.edu.unc.famaf.redditreader.backend;

import android.util.Log;

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
public class GetTopPostsTask {


    public List<PostModel> getTopPosts() throws IOException {
        List<PostModel> postList = new ArrayList<>();
        String exceptionName = "";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL
                    ("https://www.reddit.com/top.json?limit=1").openConnection();
            conn.setRequestMethod("GET");
            InputStream input = conn.getInputStream();


            //supongo q aca tengo q llamar a algo del parse para q me parsee el json q me viene
            //y despues devolver esto en la lista postList

            Parser parser = new Parser();
            Listing endListing = parser.readJsonStream(input);

            return postList;

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException: ","exceptions"+e.getMessage());
            exceptionName="OutOfMemoryError";
            return null;
        }
    }
}
