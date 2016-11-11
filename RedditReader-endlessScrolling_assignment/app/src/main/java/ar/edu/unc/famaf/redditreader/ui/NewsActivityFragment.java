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

    private View v;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);

        if(!Backend.getInstance().isConnected(getContext())){
            showToast(getContext());
        }
        if(Backend.getInstance().isConnected(getContext()) ||
                !Backend.getInstance().isEmpty(getContext())){
            //si hay conexion => va a persistir
            //si no hay conexion pero hay datos en la bd => leer de alli
            Backend.getInstance().getNextPosts(this,getContext());
        }
        return v;
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        ListView postLv = (ListView) v.findViewById(R.id.postLV);
        PostAdapter adapter = new PostAdapter(getContext(), R.layout.porst_row, posts);
        postLv.setAdapter(adapter);
        //luego voy a tener q llamar a notifyDataSetChanged() en el Postadapter p/actualizar
    }

    public void showToast(Context c){
        CharSequence text = "No Internet connection";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(c, text, duration);
        toast.show();
    }
}