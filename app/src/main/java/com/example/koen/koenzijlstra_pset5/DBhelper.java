package com.example.koen.koenzijlstra_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "lists.db";
    private static final int DATABASE_VERSION = 4;

    // MASTER TABLE NAME, COLUMNS
    private static final String MASTERTABLE_NAME = "MASTER";
    private static final String _MASTERID = "_id";
    private static final String LISTNAME = "listname";


    // TO DO ITEMS TABLE NAME AND COLUMNS
    private static final String TODOTABLE_NAME = "todos";
    private static final String _TODOID = "_id";
    private static final String TODO = "todo";
    private static final String CHECKED = "checked";
    // A COLUMN FOR WHICH OF THE MASTER LISTS IT BELONGS TO
    private static final String MASTER = "master";

    private static final String ex1 = "A list";
    private static final String ex2 = "Hold to delete";
    private static final String[] extodos = {"Add to-dos below", "Check the checkboxes of the things you did", "Hold a to-do to delete it"};


    DBhelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    // oncreate
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table for master lists
        db.execSQL("CREATE TABLE " + MASTERTABLE_NAME + "(" +
                _MASTERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LISTNAME + " TEXT NOT NULL);");

        // create table for all todo items
        db.execSQL("CREATE TABLE " + TODOTABLE_NAME + "(" +
                _TODOID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MASTER + " INTEGER NOT NULL, " +
                TODO + " TEXT NOT NULL, " +
                CHECKED + " BOOLEAN NOT NULL);");

        firsttime(db);
    }

    private void firsttime(SQLiteDatabase db){

        ContentValues example1 = new ContentValues();
        ContentValues example2 = new ContentValues();
        example1.put(LISTNAME, ex1);
        example2.put(LISTNAME, ex2);

        db.insert(MASTERTABLE_NAME, null, example1);
        db.insert(MASTERTABLE_NAME,null, example2);

        Integer idmaster = null;

        // for master example
        String[] mastertablevalues = new String[]{_MASTERID, LISTNAME};
        Cursor cursor = db.query(MASTERTABLE_NAME, mastertablevalues, LISTNAME + "='" + ex1 + "'", null,null,null,null);
        if (cursor.moveToFirst()){
            idmaster = cursor.getInt(0);
        }
        cursor.close();

        // insert todo_examples
        if (idmaster != null){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(MASTER, idmaster);
//            contentValues.put(TODO, "HEY");
//            contentValues.put(CHECKED, Boolean.FALSE);
//            db.insert(TODOTABLE_NAME, null, contentValues);
            for(int i = 0; i < extodos.length; i++){
                ContentValues contentValues = new ContentValues();
                contentValues.put(MASTER, idmaster);
                contentValues.put(TODO, extodos[i]);
                contentValues.put(CHECKED, Boolean.FALSE);
                db.insert(TODOTABLE_NAME, null, contentValues);
            }
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TODOTABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MASTERTABLE_NAME);
        // create database
        onCreate(db);
    }
}