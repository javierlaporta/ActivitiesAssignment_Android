package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;

import ar.edu.unc.famaf.redditreader.ui.PostsIteratorListener;

/**
 * Created by javier on 09/11/16.
 */
public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getNextPosts(final PostsIteratorListener listener, Context context) {
        //aca voy a tener q hacer que me lea de a 5 posts accediendo a la base de datos
        //voy a tener q llamar a listener.nextPost

    }
}


