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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.ui.NewsActivity.SectionsPagerAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private View v;
    ListView lvItems = null;
    List<PostModel> postsList = new ArrayList<>();
    PostAdapter adapter;
    OnPostItemSelectedListener postSelected;
    int mPosition;
    String mTabReddit;

    public NewsActivityFragment() {
    }

    public static NewsActivityFragment newInstance(int sectionNumber) {
        NewsActivityFragment fragment = new NewsActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
//Ojo onAttach is deprecated. PREGUNTAR COMO HACER PARA NO USAR ESTO! Android Developer lo usa
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        postSelected = (OnPostItemSelectedListener) activity;
    }

    @Override
    /**
     *Con esto puedo usar mTabReddit para cambiar la descarga de hot, top, new mas adelante
     **/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPosition = getArguments().getInt(ARG_SECTION_NUMBER);
        }catch (Exception e){
            mPosition = -1;
        }
        switch (mPosition) {
            case 0:
                mTabReddit = "hot";
                break;
            case 1:
                mTabReddit = "new";
                break;
            case 2:
                mTabReddit = "top";
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_news, container, false);
        lvItems = (ListView) v.findViewById(R.id.postLV);
        adapter = new PostAdapter(getContext(), R.layout.porst_row, postsList);
        lvItems.setAdapter(adapter);
        try {
            mPosition = getArguments().getInt(ARG_SECTION_NUMBER);
        }catch (Exception e){
            mPosition = -1;
        }
        if (mPosition != -1) {
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PostModel post = (PostModel) lvItems.getItemAtPosition(position);
                    postSelected.onPostItemPicked(post);
                }
            });

            if (!Backend.getInstance(mPosition).isConnected(getContext())) {
                showToast(getContext());
            }
            if (Backend.getInstance(mPosition).isConnected(getContext()) ||
                    !Backend.getInstance(mPosition).isEmpty(getContext())) {
                //si hay conexion => va a persistir
                //si no hay conexion pero hay datos en la bd => leer de alli
                Backend.getInstance(mPosition).getNextPosts(this, getContext(), true, mTabReddit);
                lvItems.setOnScrollListener(new EndlessScrollListener() {
                    @Override
                    public boolean onLoadMore(int page, int totalItemsCount) {
                        Backend.getInstance(mPosition).getNextPosts(NewsActivityFragment.this, getContext(),
                                false, mTabReddit);
                        return true; // ONLY if more data is actually being loaded; false otherwise.
                    }
                });
            }
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