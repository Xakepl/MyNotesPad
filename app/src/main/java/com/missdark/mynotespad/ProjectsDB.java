package com.missdark.mynotespad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
public class ProjectsDB{
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_PATH = "Path";
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_DATE = 2;
    private static final int NUM_COLUMN_PATH = 3;
    private SQLiteDatabase mDataBase;
    ArrayList<Projects> arr;
    public ProjectsDB(Context context){
        OpenHelperDB mOpenHelper = new OpenHelperDB(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }
    public long insert(String name, String date, String path){
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_PATH, path);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }
    public int update(Projects md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_DATE, md.getDate());
        cv.put(COLUMN_PATH, md.getPath());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }
    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }
    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }
    public Projects select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_NAME);
        String date = mCursor.getString(NUM_COLUMN_DATE);
        String path = mCursor.getString(NUM_COLUMN_PATH);
        return new Projects(id, name, date, path);
    }
    public Boolean isNameExists(String name){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?";
        Cursor cursor = mDataBase.rawQuery(query, new String[]{name});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }

    public ArrayList<Projects> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);
        arr = new ArrayList<Projects>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String Name = mCursor.getString(NUM_COLUMN_NAME);
                String Date = mCursor.getString(NUM_COLUMN_DATE);
                String Path = mCursor.getString(NUM_COLUMN_PATH);
                arr.add(new Projects(id, Name, Date, Path));
            } while (mCursor.moveToNext());
        }
        return arr;
    }
    public class OpenHelperDB extends SQLiteOpenHelper {

        public OpenHelperDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_PATH + " TEXT ); ";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}