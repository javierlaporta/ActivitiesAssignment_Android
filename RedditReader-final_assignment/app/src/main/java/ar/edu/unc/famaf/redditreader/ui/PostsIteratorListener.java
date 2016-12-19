package ar.edu.unc.famaf.redditreader.ui;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by javier on 10/11/16.
 */
public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);
}
