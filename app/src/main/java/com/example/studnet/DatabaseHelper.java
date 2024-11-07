package com.example.studnet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String dbname="test.db";
    private static final String col_id="id";
    private static final String tablename="stud";
    private static final String stud_name="name";
    private static final String attendance="attend";
    private static final String marks="marks";




    public DatabaseHelper( Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+tablename +"(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,marks INTEGER, attend INTEGER)");

        db.execSQL("INSERT INTO stud (id,name) values(1234,'omkar')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);
        onCreate(db);

    }

    public boolean addstudent(String name,int mark,int attend)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(stud_name,name);
        cv.put(marks,mark);
        cv.put(attendance,attend);
        long res =db.insert(tablename,null,cv);
        return res != -1;
    }

    public boolean updatestudent(String name,int mark,int attend,int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(stud_name,name);
        cv.put(marks,mark);
        cv.put(attendance,attend);

        long res =db.update(tablename,cv,col_id+ "=?" ,new String[]{String.valueOf(id)});
        return res != -1;
    }

    public Cursor getall(){
        SQLiteDatabase db = this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+tablename,null);
    }

    public boolean delstud(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        int res=db.delete(tablename,col_id+ "=?" ,new String[]{String.valueOf(id)});
        return res>0;

    }

    public boolean markatt(int id,int attend)

    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(attendance,attend);
        int res=db.update(tablename,cv,col_id+"=?",new String[]{String.valueOf(id)});
        return  res>0;

    }



}
