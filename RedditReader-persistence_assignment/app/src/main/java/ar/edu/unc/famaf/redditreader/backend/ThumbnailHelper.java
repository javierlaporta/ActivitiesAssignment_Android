package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by javier on 07/11/16.
 */
public class ThumbnailHelper {
    private RedditDBHelper db;
    private SQLiteDatabase writableDataBase;
    private SQLiteDatabase readbleDataBase;

    public ThumbnailHelper(Context context){
        db = new RedditDBHelper(context);
        writableDataBase = db.getWritableDatabase();
        readbleDataBase = db.getReadableDatabase();
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImageFromByteArray(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public Bitmap getImage(String imageUrl) {
        Bitmap bitmap = null;
//        String whereClause = db.POST_TABLE_IMAGEURL + "= '" + imageUrl + "'";
        Cursor cursor = readbleDataBase.rawQuery(
                " SELECT * FROM " + RedditDBHelper.POST_TABLE +
                " WHERE " + RedditDBHelper.POST_TABLE_IMAGEURL + "= '" + imageUrl + "'" +";",
                null);

        if (cursor.moveToFirst() && !cursor.isNull(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_BITMAP))) {
            bitmap = getImageFromByteArray(cursor.getBlob(cursor.getColumnIndex(RedditDBHelper.POST_TABLE_BITMAP)));
        }
        return bitmap;
    }


}
