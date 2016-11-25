package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import android.os.AsyncTask;

/**
 * Created by javier on 07/10/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;
    private HashMap<String, Bitmap> map = new HashMap<>();

    public class ViewHolder {
        public final TextView authorTv;
        public final ImageView imageResourceUrlIv;
        public final TextView titleTv;
        public final TextView dateTv;
        public final TextView commentTv;
        public final ProgressBar progressBar;
        public int position;
        boolean isDownloading;

        public ViewHolder(ImageView imageResourceUrlIv, TextView authorTv, TextView titleTv,
                          TextView commentTv, TextView dateTv, ProgressBar progressBar, int position){
            this.authorTv = authorTv;
            this.imageResourceUrlIv = imageResourceUrlIv;
            this.titleTv = titleTv;
            this.commentTv = commentTv;
            this.dateTv = dateTv;
            this.progressBar = progressBar;
            this.position = position;
        }
    }

    private class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url =params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try{
                connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                //bitmap =BitmapFactory.decodeStream(is,null,options);
                bitmap =BitmapFactory.decodeStream(is,null,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    public PostAdapter(Context context, int textViewResourceId, List<PostModel> postLst) {
        super(context, textViewResourceId);
        this.postLst = postLst;
    }
    @Override
    public int getCount() {
        return postLst.size();
    }

    @Override
    public int getPosition(PostModel item) {
        return postLst.indexOf(item);
    }

    @Override
    public PostModel getItem(int position) {
        return postLst.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.porst_row, parent, false);
        }
        if(convertView.getTag() == null){
            viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.imageReddit),
                    (TextView) convertView.findViewById(R.id.headReddit),
                    (TextView) convertView.findViewById(R.id.centerReddit),
                    (TextView)convertView.findViewById(R.id.numCommentReddit),
                    (TextView) convertView.findViewById(R.id.dateReddit),
                    (ProgressBar)convertView.findViewById(R.id.progressBar),
                    position);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.progressBar.setVisibility(ProgressBar.VISIBLE);
        final PostModel post = postLst.get(position);
        Bitmap bitmap = map.get(post.getimageResourceUrl());
        if (bitmap != null) {
            viewHolder.imageResourceUrlIv.setImageBitmap(bitmap);
            viewHolder.progressBar.setVisibility(ProgressBar.GONE);
        } else {
            URL[] urlArray = new URL[1];
            try {
                urlArray[0] = new URL(post.getimageResourceUrl());
            } catch (MalformedURLException e) {
                viewHolder.progressBar.setVisibility(ProgressBar.GONE);
                viewHolder.imageResourceUrlIv.setImageResource(R.mipmap.ic_launcher);
                map.put(post.getimageResourceUrl(),
                        BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher));
                urlArray[0] = null;
                e.printStackTrace();
            }
            if (!viewHolder.isDownloading && urlArray[0]!= null) {
                viewHolder.isDownloading = true;
                new DownloadImageAsyncTask() {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (position == viewHolder.position) {
                            if (bitmap != null) {
                                viewHolder.imageResourceUrlIv.setImageBitmap(bitmap);
                                map.put(post.getimageResourceUrl(), bitmap);
                            } else {
                                viewHolder.imageResourceUrlIv.setImageResource(R.mipmap.ic_launcher);
                                map.put(post.getimageResourceUrl(),
                                        BitmapFactory.decodeResource(getContext().
                                                getResources(), R.mipmap.ic_launcher));
                            }
                        } else {
                            viewHolder.imageResourceUrlIv.setImageBitmap(bitmap);
                        }
                        viewHolder.progressBar.setVisibility(ProgressBar.GONE);
                        viewHolder.isDownloading = false;
                    }
                }.execute(urlArray);
            }

        }
        viewHolder.authorTv.setText(post.getAuthor());
        viewHolder.titleTv.setText(post.getTitle());
        viewHolder.commentTv.setText(String.valueOf(post.getComment()));
        viewHolder.dateTv.setText(post.getDate());
        return convertView;
    }
}