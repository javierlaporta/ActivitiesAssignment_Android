package ar.edu.unc.famaf.redditreader.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by javier on 26/11/16.
 */
public class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(URL... params) {
        URL url =params[0];
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try{
            connection = (HttpURLConnection)url.openConnection();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is,null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
