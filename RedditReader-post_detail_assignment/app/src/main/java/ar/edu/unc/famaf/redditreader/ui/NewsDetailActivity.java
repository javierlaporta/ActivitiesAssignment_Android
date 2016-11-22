package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import ar.edu.unc.famaf.redditreader.R;

///**
// * Created by javier on 20/11/16.
// */
public class NewsDetailActivity extends AppCompatActivity {
    public final static String POST_TITLE = "ar.edu.unc.famaf.redditreader.TITLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);
        Intent intent = getIntent();
        String title = intent.getExtras().getString(POST_TITLE);
        TextView textView =(TextView) findViewById(R.id.postDetailTitle);
        textView.setText(title);
    }
}
