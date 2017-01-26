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
        String tabReddit = (String) params[2];
        SQLiteDatabase writableDatabase = db.getWritableDatabase();
//          borrar la base de datos vieja y pisarla con los 50 nuevos
        String whereClause = RedditDBHelper.POST_TABLE_TABREDDIT + " = " + "'" + tabReddit + "'";
        writableDatabase.delete(RedditDBHelper.POST_TABLE, whereClause, null);
        for(int i=0; i< postList.size();i++){
            ContentValues values = new ContentValues();
            values.put(RedditDBHelper.POST_TABLE_TITLE, postList.get(i).getTitle());
            values.put(RedditDBHelper.POST_TABLE_AUTHOR, postList.get(i).getAuthor());
            values.put(RedditDBHelper.POST_TABLE_SUBREDDIT, postList.get(i).getSubreddit());
            values.put(RedditDBHelper.POST_TABLE_DATE,postList.get(i).getDate());
            values.put(RedditDBHelper.POST_TABLE_COMMENT,postList.get(i).getComment());
            values.put(RedditDBHelper.POST_TABLE_LINKWEB, postList.get(i).getLinkWeb());
            values.put(RedditDBHelper.POST_TABLE_IMAGEURL,postList.get(i).getimageResourceUrl());
            values.put(RedditDBHelper.POST_TABLE_PREVIEW, postList.get(i).getPreview());
            values.put(RedditDBHelper.POST_TABLE_TABREDDIT, tabReddit);
            writableDatabase.insert(RedditDBHelper.POST_TABLE, null, values);
        }
        return null;
    }
}
