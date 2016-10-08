package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.PostAdapter;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
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
        //me parece que aca deberia hacer una instancia de backend con getinstance
        // y despues una instancia del adapter y asignarla a la lista qe devuelve el backend

        Backend backend = Backend.getInstance();
        List<PostModel> postLst = backend.getTopPosts();

        PostAdapter adapter = new PostAdapter(getContext(),R.layout.porst_row,postLst);
        View v =  inflater.inflate(R.layout.fragment_news, container, false);
        ListView postLv = (ListView) v.findViewById(R.id.postLV);
        postLv.setAdapter(adapter);

        return v;
    }


}
