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

import java.util.ArrayList;
import java.util.List;
import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        final ListView postLv = (ListView) v.findViewById(R.id.postLV);
        RedditDBHelper db = new RedditDBHelper(getContext(),1);
        RedditDBHelper[] dbArray = new RedditDBHelper[1];
        dbArray[0] = db;

        if(!isConnected(getActivity())){
            //buildDialog(getActivity()).show();
            List<PostModel> postModelList = new ArrayList<>();
            SQLiteDatabase readableDatabase = db.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery("SELECT * FROM " + db.POST_TABLE, null);

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
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.porst_row, postModelList);
            postLv.setAdapter(adapter);
        }
        else {
            new GetTopPostsTask() {
                @Override
                protected void onPostExecute(List<PostModel> postModels) {
                    super.onPostExecute(postModels);
                    PostAdapter adapter = new PostAdapter(getContext(), R.layout.porst_row, postModels);
                    postLv.setAdapter(adapter);
                }
            }.execute(dbArray);
        }

        return v;
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null) { // tengo conexion a internet
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI ||
                    netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You haven't internet connection");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }

}

