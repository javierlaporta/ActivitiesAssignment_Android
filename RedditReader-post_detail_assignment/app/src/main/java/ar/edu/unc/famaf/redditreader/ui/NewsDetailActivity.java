package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

///**
// * Created by javier on 20/11/16.
// */
public class NewsDetailActivity extends AppCompatActivity {
    public final static String POST_MODEL = "ar.edu.unc.famaf.redditreader.TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        Intent intent = getIntent();
        PostModel post = (PostModel) intent.getSerializableExtra(POST_MODEL);
        String title = post.getTitle();
        String author = post.getAuthor();
        TextView textViewAuthor = (TextView) findViewById(R.id.postDetailAuthor);
        TextView textViewTitle =(TextView) findViewById(R.id.postDetailTitle);
        textViewTitle.setText(title);
        textViewAuthor.setText(author);
    }
}
