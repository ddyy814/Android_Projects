package edu.sjsu.android.healthyconsultant;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;
import java.util.HashMap;

public class UserContentProvider extends ContentProvider{

    public static final String PROVIDER_NAME = "edu.sjsu.android.healthyconsultant.UserManager";
    static final String URL = "content://" + PROVIDER_NAME + "/user_info";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int HEIGHT = 1;
    static final int USER_HEIGHT = 2;

    static HashMap<String, String> HEIGHT_PROJECTION_MAP;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "user_info", HEIGHT);
        uriMatcher.addURI(PROVIDER_NAME, "user_info/#", USER_HEIGHT);
    }

    SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate(){
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return (sqLiteDatabase == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        long rowID = sqLiteDatabase.insert(DatabaseHelper.DATABASE_TABLE, "", values);
        Uri uri1 = null;
        if(rowID > 0){
            uri1 = ContentUris.withAppendedId(CONTENT_URI, rowID);
        }else{
            try{
                throw new SQLException("Failed to insert" + uri);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return uri1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        int count = 0;
        switch (uriMatcher.match(uri)){
            case HEIGHT:
                count = sqLiteDatabase.update(DatabaseHelper.DATABASE_TABLE, values, selection,
                        selectionArgs);
                break;
            case USER_HEIGHT:
                count = sqLiteDatabase.update(
                        DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.UHeight
                        + " = "
                        + uri.getPathSegments().get(1)
                        + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        int cnt = 0;
        cnt = sqLiteDatabase.delete(DatabaseHelper.DATABASE_TABLE,null,null);
        return cnt;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.DATABASE_TABLE);
        switch (uriMatcher.match(uri)){
            case HEIGHT:
                qb.setProjectionMap(HEIGHT_PROJECTION_MAP);
                break;
            case USER_HEIGHT:
                qb.appendWhere(DatabaseHelper.UHeight + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        if(sortOrder == null || sortOrder == ""){
            sortOrder = DatabaseHelper.UHeight;
        }
        Cursor c = qb.query(sqLiteDatabase, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case HEIGHT:
                return "vnd.android.cursor.dir/vnd.example.students";
            case USER_HEIGHT:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
