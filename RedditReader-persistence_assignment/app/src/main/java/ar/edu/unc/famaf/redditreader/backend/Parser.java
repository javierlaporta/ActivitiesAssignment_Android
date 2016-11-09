package ar.edu.unc.famaf.redditreader.backend;

import android.text.format.DateUtils;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.util.JsonToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

/**
 * Created by javier on 20/10/16.
 */
public class Parser {

    public Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try {
            return readDataObject(reader);
        } finally {
            reader.close();
        }
    }

    //leyendo el primer json object
    public Listing readDataObject(JsonReader reader) throws IOException{
        Listing listing = new Listing();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("data")) {
                listing = readData(reader);
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return  listing;
    }
    //leyendo data
    public Listing readData(JsonReader reader) throws IOException{
        String after = null;
        String before = null;
        List<PostModel> children = new ArrayList<>();
        Listing listing2 = new Listing();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            JsonToken check = reader.peek();

            if (name.equals("after") && check != JsonToken.NULL) {
                after = reader.nextString();
            }else if(name.equals("before") && check != JsonToken.NULL){
                before = reader.nextString();
            }else if(name.equals("children") && check != JsonToken.NULL) {
                reader.beginArray();
                while (reader.hasNext()) {
                    children.add(readChildrenArray(reader));
                }
                reader.endArray();
            }else {
                reader.skipValue();
            }
        }
        listing2.setAfter(after);
        listing2.setBefore(before);
        listing2.setChildren(children);
        return listing2;
    }


    public PostModel readChildrenArray(JsonReader reader) throws IOException{
        PostModel post = new PostModel();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("data")) {
                reader.beginObject();
                post = readT3(reader);
                reader.endObject();
            }else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return  post;
    }

    public  PostModel readT3 (JsonReader reader) throws  IOException{
        PostModel postModelT3 = new PostModel();
        String title = null;
        String author = null;
        String num_comments = null;
        String thumbnail = null;
        Long millis;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String created = null;

        while (reader.hasNext()) {
            String name = reader.nextName();
            JsonToken check = reader.peek();

            if (name.equals("title") && check != JsonToken.NULL) {
                title = reader.nextString();
            }else if(name.equals("subreddit") && check != JsonToken.NULL){
                author = reader.nextString();
            }else if (name.equals("num_comments")&& check != JsonToken.NULL){
                num_comments = reader.nextString();
            }else  if (name.equals("thumbnail") && check != JsonToken.NULL) {
                thumbnail = reader.nextString();
            }else if (name.equals("created_utc") && check != JsonToken.NULL){
                millis = reader.nextLong();
                long currentTime = System.currentTimeMillis();
                Date date = new Date(millis* 1000);
                created = DateUtils.getRelativeTimeSpanString(date.getTime(),
                        currentTime, DateUtils.MINUTE_IN_MILLIS).toString();
            }
            else {
                reader.skipValue();
            }
        }
        postModelT3.setAuthor(author);
        postModelT3.setComment(num_comments);
        postModelT3.setTitle(title);
        postModelT3.setimageResourceUrl(thumbnail);
        postModelT3.setDate(created);
        return  postModelT3;
    }
}


