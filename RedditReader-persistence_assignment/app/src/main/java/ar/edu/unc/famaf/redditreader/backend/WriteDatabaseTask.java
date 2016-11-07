package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by javier on 07/11/16.
 */
public class WriteDatabaseTask extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object... params) {
        List<PostModel> postList = (List<PostModel>) params[0];
        RedditDBHelper db = (RedditDBHelper) params[1];
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
//          borrar la base de datos vieja y pisarla con los 50 nuevos
        writableDatabase.delete(RedditDBHelper.POST_TABLE, null, null);
//        db.onUpgrade(writableDatabase, 1, 2);
        for(int i=0; i< postList.size();i++){
            String title = postList.get(i).getTitle();
            String author = postList.get(i).getAuthor();
            String date = postList.get(i).getDate();
            String comment= postList.get(i).getComment();
            String image = postList.get(i).getimageResourceUrl();
            ContentValues values = new ContentValues();
            values.put(RedditDBHelper.POST_TABLE_TITLE, title);
            values.put(RedditDBHelper.POST_TABLE_AUTHOR, author);
            values.put(RedditDBHelper.POST_TABLE_DATE,date);
            values.put(RedditDBHelper.POST_TABLE_COMMENT,comment);
            values.put(RedditDBHelper.POST_TABLE_IMAGEURL,image);
            writableDatabase.insert(RedditDBHelper.POST_TABLE, null, values);
        }
        return null;
    }
}
