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
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.porst_row, null);
        }

        //Aca tengo que tratar de hacer la vista como se veria en el xml
        //ademas necesito implementar el xml post_row con lo del lab 2 para
        //poder tener los atributos por ejemplo headreddit etc

        PostModel pm = postLst.get(position);

        TextView authorText = (TextView) convertView.findViewById(R.id.headReddit);
        ImageView imageResourceId = (ImageView) convertView.findViewById(R.id.imageReddit);
        TextView titleText = (TextView) convertView.findViewById(R.id.centerReddit);

        authorText.setText(pm.getAuthor());
        imageResourceId.setImageResource(pm.getImageResourceId());
        titleText.setText(pm.getTitle());

        return convertView;
    }
}
