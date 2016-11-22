package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.ThumbnailHelper;
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

        String subreddit = post.getSubreddit();
        String title = post.getTitle();
        String author = post.getAuthor();
        String date = post.getDate();
        String thumbnail = post.getimageResourceUrl();

        TextView textViewSubreddit = (TextView) findViewById(R.id.postDetailSubreddit);
        TextView textViewTitle =(TextView) findViewById(R.id.postDetailTitle);
        TextView textViewAuthor = (TextView) findViewById(R.id.postDetailAuthor);
        TextView textViewDate = (TextView) findViewById(R.id.postDetailDate);
        Bitmap bitmap = new ThumbnailHelper(this).getImage(thumbnail);
        if (bitmap != null){
            ImageView imageView = (ImageView) findViewById(R.id.postDetailThumnail);
            imageView.setImageBitmap(bitmap);
        }
        textViewSubreddit.setText(subreddit);
        textViewTitle.setText(title);
        textViewAuthor.setText(author);
        textViewDate.setText(date);
    }
}
