package hanium.mobile.mat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

public class CBrandDBHelper extends SQLiteOpenHelper {

    //final static private String URL = "http://mat.dothome.co.kr/register.php";
    private Map<String, String> parameter;

    private final static String DB_NAME = "chickenbrand_db";
    public final static String TABLE_NAME = "chickenbrand_table";
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_IMAGE = "image";


    public CBrandDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_NAME + " TEXT, " + COL_IMAGE + " BLOB);");


		//샘플 데이터
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '교촌치킨', null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
