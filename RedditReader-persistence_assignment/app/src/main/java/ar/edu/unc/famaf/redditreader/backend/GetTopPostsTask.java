package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
public class GetTopPostsTask extends AsyncTask<RedditDBHelper, Void, List<PostModel>> {

    @Override
    protected List<PostModel> doInBackground(RedditDBHelper...params) {
        InputStream input = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL
                    ("https://www.reddit.com/top.json?limit=50").openConnection();
            conn.setRequestMethod("GET");
            input = conn.getInputStream();
            Parser parser = new Parser();
            Listing listing = parser.readJsonStream(input);
            List<PostModel> postList = listing.getChildren();
            //esto va a ir en la task nueva
            RedditDBHelper dataBase = params[0];
            String nameTable = "postReddit";
            SQLiteDatabase db = dataBase.getWritableDatabase();


            for(int i=0; i< postList.size();i++){
                String title = postList.get(i).getTitle();
                String author = postList.get(i).getAuthor();
                String date = postList.get(i).getDate();
                String comment= postList.get(i).getComment();
                String image = postList.get(i).getimageResourceUrl();
//                String sql =
//                        "INSERT INTO "+nameTable+" (title, author, date, comment, image) "+
//                        "VALUES "+"(" + title+","+author+","+date+","+comment+","+image+")";
//                db.execSQL(sql);
// Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(dataBase.POST_TABLE_TITLE, title);
                values.put(dataBase.POST_TABLE_AUTHOR, author);
                values.put(dataBase.POST_TABLE_DATE,date);
                values.put(dataBase.POST_TABLE_COMMENT,comment);
                values.put(dataBase.POST_TABLE_IMAGE,image);
// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(dataBase.POST_TABLE, null, values);
            }


            return  postList;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException: ","exceptions"+e.getMessage());
            return null;
        }
    }
}