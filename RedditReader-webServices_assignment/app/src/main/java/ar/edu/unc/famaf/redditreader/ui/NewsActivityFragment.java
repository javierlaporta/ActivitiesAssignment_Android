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
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask;
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
        View v =  inflater.inflate(R.layout.fragment_news, container, false);
        final ListView postLv = (ListView) v.findViewById(R.id.postLV);

        new GetTopPostsTask(){
            @Override
            protected void onPostExecute(List<PostModel> postModels) {
                super.onPostExecute(postModels);
                PostAdapter adapter = new PostAdapter(getContext(),R.layout.porst_row,postModels);
                postLv.setAdapter(adapter);
            }
        }.execute();

        return v;
    }


}
