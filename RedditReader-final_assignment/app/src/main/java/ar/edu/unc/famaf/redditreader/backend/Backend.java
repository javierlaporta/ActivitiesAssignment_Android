package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.NewsActivity;
import ar.edu.unc.famaf.redditreader.ui.PostsIteratorListener;

/**
 * Created by javier on 09/11/16.
 */
public class Backend {
    private static List<BackendTab> mBackendTabInstances;

    private Backend() {
        mBackendTabInstances = new ArrayList<>();
        for (int i=0; i< NewsActivity.NUM_TABS; i++) {
            mBackendTabInstances.add(i, new BackendTab());
        }
    }

    public static BackendTab getInstance(int position) {
        try {
            return mBackendTabInstances.get(position);
        }catch(Exception e){
            mBackendTabInstances = new ArrayList<>();
            for (int i = 0; i < NewsActivity.NUM_TABS; ++i) {
                mBackendTabInstances.add(i, new BackendTab());
            }
            return mBackendTabInstances.get(position);
        }
    }

    public static class BackendTab {
        private int from = 0;
        private int UMBRAL = 5;

        private BackendTab() {
        }

        public void getNextPosts(final PostsIteratorListener listener, final Context context,
                                 Boolean write, final String tabReddit) {

//            final RedditDBHelper db = new RedditDBHelper(context);
            final RedditDBHelper db = RedditDBHelper.getInstance(context);
            String[] tabRedditArray = new String[1];
            tabRedditArray[0] = tabReddit;

            if (isConnected(context) && write) {
                new GetTopPostsTask() {
                    @Override
                    protected void onPostExecute(final List<PostModel> postModels) {
                        super.onPostExecute(postModels);
                        Object[] objectArray = new Object[3];
                        objectArray[0] = postModels;
//                        objectArray[1] = db;
                        objectArray[1] = context;
                        objectArray[2] = tabReddit;
                        new WriteDatabaseTask() {
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                readPost(listener, context, from, UMBRAL, tabReddit);
                                from += UMBRAL;
                            }
                        }.execute(objectArray);
                    }
                }.execute(tabRedditArray);
            } else {
                readPost(listener, context, from, UMBRAL, tabReddit);
                from += UMBRAL;
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

        public boolean isEmpty(Context context) {//para saber si la base de datos esta vacia
//            RedditDBHelper db = new RedditDBHelper(context);
            RedditDBHelper db = RedditDBHelper.getInstance(context);
            SQLiteDatabase readableDatabase = db.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(
                    " SELECT * FROM " + RedditDBHelper.POST_TABLE, null);
            return (!cursor.moveToFirst());
        }

        private void readPost(PostsIteratorListener listener, Context context, int from, int UMBRAL,
                              String tabReddit) {
//            RedditDBHelper db = new RedditDBHelper(context);
            RedditDBHelper db = RedditDBHelper.getInstance(context);
            List<PostModel> postModelList = new ArrayList<>();
            SQLiteDatabase readableDatabase = db.getReadableDatabase();
            String whereClause = " WHERE " + RedditDBHelper.POST_TABLE_TABREDDIT + " = " + "'" + tabReddit + "'";
            Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + RedditDBHelper.POST_TABLE +
                    whereClause + " LIMIT " + Integer.toString(from) + "," + Integer.toString(UMBRAL), null);

            if (cursor.moveToFirst()) {
                do {
                    PostModel postModel = new PostModel();
                    postModel.setTitle(cursor.getString(1));
                    postModel.setAuthor(cursor.getString(2));
                    postModel.setSubreddit(cursor.getString(3));
                    postModel.setDate(cursor.getString(4));
                    postModel.setComment(cursor.getString(5));
                    postModel.setimageResourceUrl(cursor.getString(6));
                    postModel.setLinkWeb(cursor.getString(7));
                    postModel.setPreview(cursor.getString(8));
                    postModelList.add(postModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            listener.nextPosts(postModelList);
        }
    }
}

