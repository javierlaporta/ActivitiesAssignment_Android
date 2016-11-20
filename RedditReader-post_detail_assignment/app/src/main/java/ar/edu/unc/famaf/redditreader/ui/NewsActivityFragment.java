package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {

    private View v;
    ListView lvItems = null;
    List<PostModel> postsList = new ArrayList<>();
    PostAdapter adapter;
    OnPostItemSelectedListener postSelected = new OnPostItemSelectedListener() {
        @Override
        public void onPostItemPicked(PostModel post) {
        }
    };//duda: para que postSelected no sea null hice esto. Hay otra manera de hacerlo?

    public NewsActivityFragment() {
    }

    public interface OnPostItemSelectedListener{
        void onPostItemPicked(PostModel post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);
        lvItems = (ListView) v.findViewById(R.id.postLV);
        adapter = new PostAdapter(getContext(), R.layout.porst_row, postsList);
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostModel post = (PostModel) lvItems.getItemAtPosition(position);
                postSelected.onPostItemPicked(post);
            }
        });

        if(!Backend.getInstance().isConnected(getContext())){
            showToast(getContext());
        }
        if(Backend.getInstance().isConnected(getContext()) ||
                !Backend.getInstance().isEmpty(getContext())){
            //si hay conexion => va a persistir
            //si no hay conexion pero hay datos en la bd => leer de alli
            Backend.getInstance().getNextPosts(this,getContext(),true);
            lvItems.setOnScrollListener(new EndlessScrollListener() {
                @Override
                public boolean onLoadMore(int page, int totalItemsCount) {
                    Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(),false);
                    return true; // ONLY if more data is actually being loaded; false otherwise.
                }
            });
        }
        return v;
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        postsList.addAll(posts);
        adapter.notifyDataSetChanged();
    }

    public void showToast(Context c){
        CharSequence text = "No Internet connection";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(c, text, duration);
        toast.show();
    }
}