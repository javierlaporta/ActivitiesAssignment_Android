package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import ar.edu.unc.famaf.redditreader.backend.Backend;

/**
 * Created by javier on 22/11/16.
 */
public class LinkWebActivity extends AppCompatActivity {
    public final static String LINK_WEB = "ar.edu.unc.famaf.redditreader.LINK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);
        Intent intent = getIntent();
        String url = intent.getExtras().getString(LINK_WEB);
        webview.getSettings().setJavaScriptEnabled(true); // to enable JavaScript execution
        webview.setWebViewClient(new WebViewClient()); // to navegate inside the webview
//        webview.getSettings().setBuiltInZoomControls(true);
        if(Backend.getInstance().isConnected(this)){
            try {
                webview.loadUrl(url);
            } catch (Exception e) {
                Toast.makeText(this, "Sorry. Could not read url", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
        }
    }

}
