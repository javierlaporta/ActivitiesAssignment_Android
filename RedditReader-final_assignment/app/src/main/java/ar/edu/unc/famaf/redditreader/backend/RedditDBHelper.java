package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by javier on 03/11/16.
 */
public class RedditDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbReddit.db";
    private static final int DATABASE_VERSION = 1;
    public static final String POST_TABLE = "postReddit";
    public static final String POST_TABLE_TITLE = "title";
    public static final String POST_TABLE_AUTHOR = "author";
    public static final String POST_TABLE_SUBREDDIT = "subreddit";
    public static final String POST_TABLE_DATE = "date";
    public static final String POST_TABLE_COMMENT = "comment";
    public static final String POST_TABLE_IMAGEURL = "imageUrl";
    public static final String POST_TABLE_BITMAP = "bitmap";
    public static final String POST_TABLE_LINKWEB = "linkWeb";
    public static final String POST_TABLE_PREVIEW = "preview";
    public static final String POST_TABLE_TABREDDIT = "tabReddit";


//    public RedditDBHelper(Context context) {
//        super(context, DATABASE_NAME , null, DATABASE_VERSION);
//    }
    private static RedditDBHelper sInstance;

    public static synchronized RedditDBHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new RedditDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public RedditDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
            + POST_TABLE+ " (_id integer primary key autoincrement,"
            +POST_TABLE_TITLE+ " text not null,"
            +POST_TABLE_AUTHOR+" text not null,"
            +POST_TABLE_SUBREDDIT+" text not null,"
            +POST_TABLE_DATE+" text not null,"
            +POST_TABLE_COMMENT+" text not null,"
            +POST_TABLE_IMAGEURL+" text not null,"
            +POST_TABLE_LINKWEB+" text not null,"
            +POST_TABLE_PREVIEW+" text,"
            + POST_TABLE_BITMAP + " blob,"
            + POST_TABLE_TABREDDIT + " text not null"
            +" );";
            db.execSQL(createSentence);

        Log.d("DB","Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB","Database updated");
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        this.onCreate(db);
    }
}
