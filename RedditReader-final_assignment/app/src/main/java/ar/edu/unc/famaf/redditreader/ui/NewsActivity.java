package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity implements OnPostItemSelectedListener {
    static final int LOGIN_REQUEST = 1;
    public final static String EMAIL_TEXT = "com.example.javier.ActivitiesAssigment_Android.EMAIL";
    public final static String POST_MODEL = "ar.edu.unc.famaf.redditreader.TITLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            Intent intentToLogin = new Intent(this, LoginActivity.class);
            startActivityForResult(intentToLogin, LOGIN_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGIN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String userEmailText = data.getExtras().getString(EMAIL_TEXT);
                TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
                textView.setText("User "+ userEmailText + " logged in");
            }
        }
    }

    @Override
    public void onPostItemPicked(PostModel post) {//interfaz de comuniccacion con fragmento
        Intent intentToDetailAct= new Intent(this,NewsDetailActivity.class);
        intentToDetailAct.putExtra(POST_MODEL,post);
        startActivity(intentToDetailAct);
    }
}
