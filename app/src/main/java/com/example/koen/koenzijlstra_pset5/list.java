package com.example.koen.koenzijlstra_pset5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class list extends AppCompatActivity {

    protected ListView listview;
    protected EditText editText;
    protected TextView tvtitle = null;
    protected Integer idmaster;
    protected Masterobject currentmaster = null;
    protected DBmanager dBmanager;
    protected ArrayList<Todo_object> todos = null;
    protected ListadapterTodo adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        editText = (EditText) findViewById(R.id.input);
        tvtitle = (TextView) findViewById(R.id.title);
        dBmanager = DBmanager.getInstance();



        if (savedInstanceState != null){
            todos = savedInstanceState.getParcelableArrayList("todos");
            tvtitle.setText(savedInstanceState.getString("title"));
            idmaster = savedInstanceState.getInt("idmaster");

        }
        else{
            Intent intent = getIntent();
            currentmaster = intent.getParcelableExtra("masterlistparce");
            idmaster = currentmaster.getId();
            tvtitle.setText(currentmaster.getName());
            todos = dBmanager.gettodos(currentmaster);

        }

        listview = (ListView) findViewById(R.id.lvtodo);
        adapter = new ListadapterTodo(getApplicationContext(), todos);
        listview.setAdapter(adapter);



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo_object todo = (Todo_object) listview.getItemAtPosition(position);
                dBmanager.deletetodo(todo);
                adapter.remove(todo);
                adapter.notifyDataSetChanged();
                return true;
                // return false;
            }
        });
    }

    @Override
    protected  void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList("todos", todos);
        savedInstanceState.putString("title", tvtitle.getText().toString());
        savedInstanceState.putInt("idmaster", idmaster);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void createRemoveListener(){
        Log.d("infunctie", "message2");
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo_object todo = (Todo_object) listview.getItemAtPosition(position);
                adapter.remove(todo);
                dBmanager.deletetodo(todo);
                adapter.notifyDataSetChanged();
                Log.d("laatst", "msg3");
                return true;
                // return false;
            }
        });
    }

//    protected void createdeleter(){
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Todo_object todo = (Todo_object) listview.getItemAtPosition(position);
//                adapter.remove(todo);
//                dBmanager.deletetodo(todo);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

    protected void addtodo(View view){
        if (editText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please add a to-do", Toast.LENGTH_SHORT).show();
        }
        else {
            HideKeys();
            dBmanager.newtodo(currentmaster, editText.getText().toString());
            editText.setText("");
            todos = new ArrayList<>();
            ArrayList<Todo_object> todos = dBmanager.gettodos(currentmaster);

            adapter = new ListadapterTodo(getApplicationContext(), todos);
            listview.setAdapter(adapter);
            createRemoveListener();
        }
    }


    public void HideKeys() {
        try  {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
