package ar.edu.unc.famaf.redditreader;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by javier on 07/10/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;

    public PostAdapter(Context context, int textViewResourceId, List<PostModel> postLst) {
        super(context, textViewResourceId);
        this.postLst = postLst;
    }

    public class ViewHolder {
        public final TextView authorTv;
        public final ImageView imageResourceIdIv;
        public final TextView titleTv;
        public final TextView dateTv;
        public final TextView commentTv;

        public ViewHolder(ImageView imageResourceIdIv, TextView authorTv, TextView titleTv,
                          TextView commentTv, TextView dateTv){
            this.authorTv = authorTv;
            this.imageResourceIdIv = imageResourceIdIv;
            this.titleTv = titleTv;
            this.commentTv = commentTv;
            this.dateTv = dateTv;
        }
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
        viewHolder.authorTv.setText(pm.getAuthor());
        viewHolder.imageResourceIdIv.setImageResource(pm.getImageResourceId());
        viewHolder.titleTv.setText(pm.getTitle());
        viewHolder.commentTv.setText(String.valueOf(pm.getComment()));
        viewHolder.dateTv.setText(pm.getDate());
        return convertView;
    }
}
