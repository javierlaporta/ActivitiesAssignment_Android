package ar.edu.unc.famaf.redditreader.model;

import android.support.annotation.Nullable;
import java.util.List;

/**
 * Created by javier on 20/10/16.
 */
public class Listing {
    private String mAfter;
    private String mBefore;
    private List<PostModel> mChildren;

    @Nullable
    public String getAfter() {
        return mAfter;
    }

    public void setAfter(String after) {
        this.mAfter = after;
    }

    @Nullable
    public String getBefore() {
        return mBefore;
    }

    public void setBefore(String mBefore) {
        this.mBefore = mBefore;
    }

    @Nullable
    public List<PostModel> getChildren() {
        return mChildren;
    }

    public void setChildren(List<PostModel> mChildren) {
        this.mChildren = mChildren;
    }
}
