package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.microedition.khronos.opengles.GL10;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.DownloadImageAsyncTask;
import ar.edu.unc.famaf.redditreader.model.PostModel;

///**
// * Created by javier on 20/11/16.
// */
public class NewsDetailActivity extends AppCompatActivity {
    public final static String POST_MODEL = "ar.edu.unc.famaf.redditreader.TITLE";
    public final static String LINK_WEB = "ar.edu.unc.famaf.redditreader.LINK";
    public HashMap<String, Bitmap> cache = new HashMap<>();
    boolean isDownloading;


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
        final String linkWeb = post.getLinkWeb();

        TextView textViewSubreddit = (TextView) findViewById(R.id.postDetailSubreddit);
        TextView textViewTitle =(TextView) findViewById(R.id.postDetailTitle);
        TextView textViewAuthor = (TextView) findViewById(R.id.postDetailAuthor);
        TextView textViewDate = (TextView) findViewById(R.id.postDetailDate);

        textViewSubreddit.setText("/r/"+subreddit);
        textViewTitle.setText(title);
        textViewAuthor.setText(author+"  •  ");
        textViewDate.setText(date);
        setPreview(post);

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

    public void setPreview (final PostModel post){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.postDetailProgressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Bitmap bitmap = cache.get(post.getPreview());
        final ImageView imageView = (ImageView) findViewById(R.id.postDetailPreview);

        if (bitmap == null) {
            URL[] urlArray = new URL[1];
            try {
                urlArray[0] = new URL(post.getPreview());
            } catch (MalformedURLException e) {
                urlArray[0] = null;
                progressBar.setVisibility(ProgressBar.GONE);
                e.printStackTrace();
            }
            if (!isDownloading && urlArray[0]!= null) {
                isDownloading = true;
                new DownloadImageAsyncTask() {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap != null && bitmap.getHeight() < GL10.GL_MAX_TEXTURE_SIZE) {
                            cache.put(post.getPreview(), bitmap);
                            imageView.setImageBitmap(bitmap);

                        }else if (bitmap != null  && bitmap.getHeight() > GL10.GL_MAX_TEXTURE_SIZE){
                            float aspect_ratio = ((float)bitmap.getHeight())/((float)bitmap.getWidth());
//                            Log.v("ASPECT" , Float.toString(aspect_ratio));
                            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,
                                    (int) ((bitmap.getWidth()*0.5)*aspect_ratio) ,
                                    (int) ((bitmap.getHeight()*0.5)*aspect_ratio), false);
                            cache.put(post.getPreview(), newBitmap);
                            imageView.setImageBitmap(newBitmap);

                        }
                        isDownloading = false;
                        progressBar.setVisibility(ProgressBar.GONE);

                    }
                }.execute(urlArray);
            }
        }else{
            imageView.setImageBitmap(bitmap);
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }
}
