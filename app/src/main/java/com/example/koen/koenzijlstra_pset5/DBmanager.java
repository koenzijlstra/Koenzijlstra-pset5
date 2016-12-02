package com.example.koen.koenzijlstra_pset5;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by Koen on 2-12-2016.
 */

// SINGLETON!!

class DBmanager {

    // all declarations for todos
    private static final String TODOTABLE_NAME = "todos";
    private static final String _TODOID = "_id";
    private static final String TODO = "todo";
    private static final String CHECKED = "checked";
    private static final String MASTER = "master";

    // all declarations for masters
    public static final String MASTERTABLE_NAME = "MASTER";
    private static final String _MASTERID = "_id";
    private static final String LISTNAME = "listname";

    private ArrayList<Masterobject> listofmasters;
    private SQLiteDatabase db;
    private DBhelper dBhelper = null;

    private static DBmanager ourInstance = new DBmanager();

    static DBmanager getInstance(){
        return ourInstance;
    }

    private DBmanager(){

    }

    // create new master list
    void newmasterlist (String listname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LISTNAME, listname);
        db.insert(MASTERTABLE_NAME, null, contentValues);

        
    }


}
