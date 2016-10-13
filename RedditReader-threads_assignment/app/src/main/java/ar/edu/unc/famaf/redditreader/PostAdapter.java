package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import android.os.AsyncTask;
/**
 * Created by javier on 07/10/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;

    public class ViewHolder {//no deberia ser private?
        public final TextView authorTv;
        //public final ImageView imageResourceIdIv;
        public final ImageView imageResourceUrlIv;
        public final TextView titleTv;
        public final TextView dateTv;
        public final TextView commentTv;

        public ViewHolder(ImageView imageResourceUrlIv, TextView authorTv, TextView titleTv,
                          TextView commentTv, TextView dateTv){//no deberia ser private?
            this.authorTv = authorTv;
            //this.imageResourceIdIv = imageResourceIdIv;
            this.imageResourceUrlIv = imageResourceUrlIv;
            this.titleTv = titleTv;
            this.commentTv = commentTv;
            this.dateTv = dateTv;
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
                bitmap =BitmapFactory.decodeStream(is,null,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            System.out.print("onPostExcecute");
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.porst_row,parent,false);
            viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.imageReddit),
                    (TextView) convertView.findViewById(R.id.headReddit),
                    (TextView) convertView.findViewById(R.id.centerReddit),
                    (TextView)convertView.findViewById(R.id.numCommentReddit),
                    (TextView) convertView.findViewById(R.id.dateReddit));
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PostModel pm = postLst.get(position);
        DownloadImageAsyncTask dwd = new DownloadImageAsyncTask();
        dwd.execute();

        viewHolder.authorTv.setText(pm.getAuthor());

        //Supongo que ahora en vez de tener esto:
        //viewHolder.imageResourceIdIv.setImageResource(pm.getImageResourceId());
        //voy a tener que hacer la asyncTask que me descargue la imagen desde el
        //url que esta seteado en backend. en el string imageResourceUrl

        viewHolder.titleTv.setText(pm.getTitle());
        viewHolder.commentTv.setText(String.valueOf(pm.getComment()));
        viewHolder.dateTv.setText(pm.getDate());
        return convertView;
    }
}