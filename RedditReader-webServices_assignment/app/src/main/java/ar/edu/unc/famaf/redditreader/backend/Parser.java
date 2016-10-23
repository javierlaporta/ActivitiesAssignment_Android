package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;

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
        //List<String> children = null;
        Listing listing2 = new Listing();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("after")) {
                after = reader.nextString();
            }else if(name.equals("before")){
                before = reader.nextString();
            }//else if(name.equals("children") && reader.peek() != JsonToken.NULL) {
             //   children = readChildrenArray(reader);
            //}
            else {
                reader.skipValue();
            }
        }
        listing2.setAfter(after);
        listing2.setBefore(before);
        //listing.setChildren(children);
        return listing2;
    }
    //public List<String> readChildrenArray(JsonReader reader){
    //    return new ArrayList<String>();
    //}

}


