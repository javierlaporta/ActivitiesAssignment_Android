package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.PostsIteratorListener;

/**
 * Created by javier on 09/11/16.
 */
public class Backend {
    private static Backend ourInstance = new Backend();
    private int from = 0;
    private static int UMBRAL = 5;

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getNextPosts(final PostsIteratorListener listener, final Context context, Boolean write) {

        final RedditDBHelper db = new RedditDBHelper(context);

        if (isConnected(context) && write){
            new GetTopPostsTask() {
                @Override
                protected void onPostExecute(final List<PostModel> postModels) {
                    super.onPostExecute(postModels);
                    Object[] objectArray = new Object[2];
                    objectArray[0]= postModels;
                    objectArray[1]= db;
                    new WriteDatabaseTask(){
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            readPost(listener,context,from,UMBRAL);
                            from+=UMBRAL;
                        }
                    }.execute(objectArray);
                }
            }.execute();
            from = 0;
        }else{
            readPost(listener,context,from,UMBRAL);
            from +=UMBRAL;
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null) { // tengo conexion a internet
            return netInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                    netInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }
    public boolean isEmpty (Context context){//para saber si la base de datos esta vacia
        RedditDBHelper db = new RedditDBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                " SELECT * FROM " + RedditDBHelper.POST_TABLE ,null);
        return (! cursor.moveToFirst());
    }

    private void readPost (PostsIteratorListener listener, Context context, int from, int UMBRAL){
        RedditDBHelper db = new RedditDBHelper(context);
        List<PostModel> postModelList = new ArrayList<>();
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + RedditDBHelper.POST_TABLE +
                " LIMIT " + Integer.toString(from) +"," + Integer.toString(UMBRAL) , null);

        if (cursor.moveToFirst()) {
            do {
                PostModel postModel = new PostModel();
                postModel.setTitle(cursor.getString(1));
                postModel.setAuthor(cursor.getString(2));
                postModel.setDate(cursor.getString(3));
                postModel.setComment(cursor.getString(4));
                postModel.setimageResourceUrl(cursor.getString(5));
                postModelList.add(postModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        listener.nextPosts(postModelList);
    }
}


