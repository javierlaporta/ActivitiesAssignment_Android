package ar.edu.unc.famaf.redditreader.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.unc.famaf.redditreader.R;


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

        return inflater.inflate(R.layout.fragment_news, container, false);

    }

}
