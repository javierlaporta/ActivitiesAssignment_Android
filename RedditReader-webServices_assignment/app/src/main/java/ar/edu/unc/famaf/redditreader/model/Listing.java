package ar.edu.unc.famaf.redditreader.model;

import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javier on 20/10/16.
 */
public class Listing {
    private String mAfter;
    private String mBefore;
    private List<String> mChildren;

    @Nullable
    public String getAfter() {
        return mAfter;
    }

    public void setAfter(String after) {
        this.mAfter = after;
    }

    //private ArrayList<String> mChilder;
    //ojo creo q es mas facil usar arrayList pero el profe me dijo q es mas prolijo usar list
}
