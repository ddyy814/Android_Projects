package edu.sjsu.android.healthyconsultant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;

public class DatabaseHelper extends SQLiteOpenHelper {


    SQLiteDatabase mDB;
    static String DATABASE_NAME = "UserManager";
    static int DATABASE_VERSION = 1;
    static final String DATABASE_TABLE = "user_info";
    public static final String UHeight = "height";
    public static final String UWeight = "weight";
    public static final String UAge = "age";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + DATABASE_TABLE + " ( " +
                UHeight + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UWeight + " DOUBLE , " +
                UAge + " INTEGER " +
                " ) ";

        db.execSQL("create table user(username text primary key, password text, first_name text, last_name text)");
        db.execSQL("create table meal(height integer primary key, img_meal blob not null)");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists user_info");
        onCreate(db);
    }

    public boolean addUser(String username, String password, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        long newRowId = db.insert("user", null, values);
        if (newRowId == -1) return false;
        return true;
    }

    public boolean existUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        return false;
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        return false;
    }

    public String getFullName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select first_name, last_name from user where username=?", new String[]{username});
        cursor.moveToFirst();
        return cursor.getString(0) + " " + cursor.getString(1);
    }




    public Bitmap getImage(int height) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bitmap = null;
        Cursor cursor = db.rawQuery("select img_meal from meal where height=?", new String[]{String.valueOf(height)}, null);
        if (cursor.moveToNext()) {
            byte[] img_meal = cursor.getBlob(1);
            bitmap = BitmapFactory.decodeByteArray(img_meal, 0, img_meal.length);
        }
        return bitmap;
    }
}
