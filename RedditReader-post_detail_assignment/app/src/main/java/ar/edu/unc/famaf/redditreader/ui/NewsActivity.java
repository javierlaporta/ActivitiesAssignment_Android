package ar.edu.unc.famaf.redditreader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity implements NewsActivityFragment.OnPostItemSelectedListener {

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

        //if (id == R.id.action_sign_in) {
          //  TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
           // textView.setText("User XXXX logged in");
           // return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostItemPicked(PostModel post) {
//        LayoutInflater inflater = (LayoutInflater)this.getSystemService
//                (this.LAYOUT_INFLATER_SERVICE);
//        View vista = inflater.inflate(R.layout.post_detail,null,false);
//        TextView textView = (TextView) vista.findViewById(R.id.postDetailTitle);
//        textView.setText(post.getTitle());
//
//        String myAuthor = post.getAuthor();
//        Toast.makeText(this,myAuthor,Toast.LENGTH_LONG).show();
        //interfaz de comuniccacion con fragmento
    }
}
