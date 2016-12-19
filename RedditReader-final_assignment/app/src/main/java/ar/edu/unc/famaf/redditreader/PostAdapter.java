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
import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.DownloadImageAsyncTask;
import ar.edu.unc.famaf.redditreader.backend.ThumbnailHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import android.os.AsyncTask;
/**
 * Created by javier on 07/10/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;

    public class ViewHolder {
        public final TextView authorTv;
        public final ImageView imageResourceUrlIv;
        public final TextView titleTv;
        public final TextView dateTv;
        public final TextView commentTv;
        public final ProgressBar progressBar;

        public ViewHolder(ImageView imageResourceUrlIv, TextView authorTv, TextView titleTv,
                          TextView commentTv, TextView dateTv, ProgressBar progressBar){
            this.authorTv = authorTv;
            this.imageResourceUrlIv = imageResourceUrlIv;
            this.titleTv = titleTv;
            this.commentTv = commentTv;
            this.dateTv = dateTv;
            this.progressBar = progressBar;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
                    (ProgressBar)convertView.findViewById(R.id.progressBar));
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PostModel pm = postLst.get(position);
        viewHolder.authorTv.setText("/r/"+pm.getSubreddit());
        viewHolder.titleTv.setText(pm.getTitle());
        viewHolder.commentTv.setText(String.valueOf(pm.getComment()));
        viewHolder.dateTv.setText(pm.getDate());
//        Descargar-leer-Imagen
        final String imageUrl = pm.getimageResourceUrl();
        Bitmap bitmap = new ThumbnailHelper(getContext()).getImage(imageUrl);
        if(bitmap == null){
            //Para ese URL la columna BITMAP es null => descargar la imagen y almacenarla en la bd
            URL myUrl;
            try {
                myUrl = new URL(pm.getimageResourceUrl());
                URL[] urlArray = new URL[1];
                urlArray[0] = myUrl;
                new DownloadImageAsyncTask() {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        viewHolder.progressBar.setVisibility(ProgressBar.GONE);
                        if(bitmap != null){
                            viewHolder.imageResourceUrlIv.setImageBitmap(bitmap);
                            new ThumbnailHelper(getContext()).saveImage(imageUrl, bitmap);
                        }
                        else{
                            viewHolder.imageResourceUrlIv.setImageResource(R.mipmap.ic_launcher);
                        }
                    }
                }.execute(urlArray);
            } catch (MalformedURLException e) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.imageResourceUrlIv.setImageResource(R.mipmap.ic_launcher);
            }
        }else{
            //Ya tenia guardado el bitmap en la columna BITMAP => leerlo de alli
            viewHolder.progressBar.setVisibility(View.GONE);
            viewHolder.imageResourceUrlIv.setImageBitmap(bitmap);
        }
        return convertView;
    }
}
