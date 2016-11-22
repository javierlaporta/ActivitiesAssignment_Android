package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    public final static String LINK_WEB = "ar.edu.unc.famaf.redditreader.LINK";
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
        final String linkWeb = post.getLinkWeb();

        TextView textViewSubreddit = (TextView) findViewById(R.id.postDetailSubreddit);
        TextView textViewTitle =(TextView) findViewById(R.id.postDetailTitle);
        TextView textViewAuthor = (TextView) findViewById(R.id.postDetailAuthor);
        TextView textViewDate = (TextView) findViewById(R.id.postDetailDate);
//        TextView textViewLinkWeb = (TextView) findViewById(R.id.postDetailLink);
        Bitmap bitmap = new ThumbnailHelper(this).getImage(thumbnail);
        if (bitmap != null){
            ImageView imageView = (ImageView) findViewById(R.id.postDetailThumnail);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 800, 500, false));
//            imageView.setImageBitmap(bitmap);
        }
        textViewSubreddit.setText("/r/"+subreddit);
        textViewTitle.setText(title);
        textViewAuthor.setText(author+"  •  ");
        textViewDate.setText(date);
//        textViewLinkWeb.setText(linkWeb);

//        if(linkWeb != null) { ESTE IF DEBERIA IR? PARA NO LANZAR LA ACTIVIDAD SI ES NULL?
            textViewTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent2WebActivity = new Intent(getApplicationContext(), LinkWebActivity.class);
                    intent2WebActivity.putExtra(LINK_WEB, linkWeb);
                    startActivity(intent2WebActivity);
                }
            });
//        }

    }
}
