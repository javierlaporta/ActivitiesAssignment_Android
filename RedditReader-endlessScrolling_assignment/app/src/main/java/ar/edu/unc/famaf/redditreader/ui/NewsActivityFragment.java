package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.backend.WriteDatabaseTask;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        final ListView postLv = (ListView) v.findViewById(R.id.postLV);
        final RedditDBHelper db = new RedditDBHelper(getContext());

        if(!isConnected(getActivity())){
            showToast(getContext());
            List<PostModel> postModelList = new ArrayList<>();
            SQLiteDatabase readableDatabase = db.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + RedditDBHelper.POST_TABLE, null);

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
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.porst_row, postModelList);
            postLv.setAdapter(adapter);
        }
        else {
//            new GetTopPostsTask() {
//                @Override
//                protected void onPostExecute(List<PostModel> postModels) {
//                    super.onPostExecute(postModels);
//                    PostAdapter adapter = new PostAdapter(getContext(), R.layout.porst_row, postModels);
//                    postLv.setAdapter(adapter);
//
//                    Object[] objectArray = new Object[2];
//                    objectArray[0]= postModels;
//                    objectArray[1]= db;
//                    new WriteDatabaseTask(){
//                    }.execute(objectArray);
//                }
//            }.execute();
//            aca hacer una instancia de backend y llamar a PostIteratorListener
            Backend.getInstance().getNextPosts(this,getContext());
        }

//        listerner

        return v;
    }

    @Override
    public void nextPosts(List<PostModel> posts) {

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

    public void showToast(Context c){
        CharSequence text = "No Internet connection";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(c, text, duration);
        toast.show();
    }
}

