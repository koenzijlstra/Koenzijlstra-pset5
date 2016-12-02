package com.example.koen.koenzijlstra_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    static final String DB_NAME = "lists.db";
    static final int DATABASE_VERSION = 1;

    // MASTER TABLE NAME, COLUMNS
    public static final String MASTERTABLE_NAME = "MASTER";
    private static final String _MASTERID = "_id";
    private static final String LISTNAME = "listname";


    // TO DO ITEMS TABLE NAME AND COLUMNS
    private static final String TODOTABLE_NAME = "todos";
    public static final String _TODOID = "_id";
    public static final String TODO = "todo";
    public static final String CHECKED = "checked";
    // A COLUMN FOR WHICH OF THE MASTER LISTS IT BELONGS TO
    private static final String MASTER = "master";

    // voorbeeld dat eerste keer er moet staan:
    public DBhelper(Context context) {
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
                TODO + " TEXT NOT NULL, " +
                MASTER + " INTEGER NOT NULL, " +
                CHECKED + " BOOLEAN NOT NULL);");


        firsttime(db);
    }

    public void firsttime(SQLiteDatabase db){
        // create the examples to show when database is created
        final String ex1 = "A list";
        final String ex2 = "Hold to delete";
        final String[] extodos = {"Add to-dos below", "Check the checkboxes of the things you did", "Hold a to-do to delete it"};

        ContentValues example1 = new ContentValues();
        example1.put(LISTNAME, ex1);
        ContentValues example2 = new ContentValues();
        example2.put(LISTNAME, ex2);

        db.insert(LISTNAME, null, example1);
        db.insert(LISTNAME,null, example2);

        Integer idmaster = null;

        // for master example
        String[] mastertablevalues = new String[]{_MASTERID, LISTNAME};
        Cursor cursor = db.query(LISTNAME, mastertablevalues, LISTNAME + "=" + ex1 + "'", null,null,null,null);
        if (cursor.moveToFirst()){
            idmaster = cursor.getInt(0);
        }
        cursor.close();

        // insert todo_examples
        if (idmaster != null){
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