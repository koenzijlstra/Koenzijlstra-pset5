package com.example.koen.koenzijlstra_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    void firstlaunch(Context context){
        if (dBhelper == null){
            dBhelper = new DBhelper(context);
            db = dBhelper.getWritableDatabase();
            listofmasters = getmasters();
        }
    }

    ArrayList<Masterobject> getmasters(){
        ArrayList<Masterobject> masters = new ArrayList<>();

        String[] tablevalues = new String[] {_MASTERID, LISTNAME};
        Cursor cursor = db.query(MASTERTABLE_NAME, tablevalues, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Masterobject master = new Masterobject(cursor.getString(1));
                master.setId(cursor.getInt(0));
                masters.add(master);
            } while (cursor.moveToNext());
        }
    cursor.close();
    return masters;
    }

    // create new master list
    void newmasterlist (String listname){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LISTNAME, listname);
        db.insert(MASTERTABLE_NAME, null, contentValues);

        Integer masterid = 0;
        String[] tablevalues = new String[] {_MASTERID, LISTNAME};
        // newest object gets highest id
        Cursor cursor = db.query(MASTERTABLE_NAME, tablevalues, LISTNAME + "=" + "'" + listname + "'", null, null, null, null);
        if (cursor.moveToFirst()){
            masterid = cursor.getInt(0);
            do {
                if (cursor.getInt(0) > masterid){
                    masterid = cursor.getInt(0);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        Masterobject masterlist = new Masterobject(listname);
        masterlist.setId(masterid);
        listofmasters.add(masterlist);
    }

    void deletemaster (Masterobject master){
        // remove the master list from master table
        db.delete(MASTERTABLE_NAME, _MASTERID + " = " + master.getId(), null);
        // remove all the todos that had were part of the master list
        db.delete(TODOTABLE_NAME, MASTER + " = " + master.getId(), null);
        listofmasters.remove(master);

    }

    void newtodo (Masterobject master, String todoinput){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MASTER, master.getId());
        contentValues.put(TODO, todoinput);
        contentValues.put(CHECKED, Boolean.FALSE);
        db.insert(TODOTABLE_NAME, null, contentValues);
    }

    void deletetodo(Todo_object todo){
        db.delete(TODOTABLE_NAME, _TODOID + " = " + todo.getId(),null);
    }

    // get all todos from certain master list (use id of master)
    ArrayList<Todo_object> gettodos(Masterobject master) {
        ArrayList<Todo_object> todos = new ArrayList<>();
        String[] tablevalues = new String[]{_TODOID, MASTER, TODO, CHECKED};
        Cursor cursor = db.query(TODOTABLE_NAME, tablevalues, MASTER + " = " + master.getId(), null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                Todo_object todo = new Todo_object(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), (cursor.getInt(3) == 1));
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return todos;
    }

    // update table in database when user checks a box. first only the view changed
    void whenchecked(Todo_object todo, Boolean checked){
        ContentValues contentValues = new ContentValues();
        contentValues.put (CHECKED, checked);
        db.update(TODOTABLE_NAME, contentValues, _TODOID + " = " + todo.getId(), null);
        todo.setChecked(checked);
    }
}
