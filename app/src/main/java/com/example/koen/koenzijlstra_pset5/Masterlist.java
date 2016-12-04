package com.example.koen.koenzijlstra_pset5;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView;
import java.util.ArrayList;

public class Masterlist extends AppCompatActivity {

    protected DBmanager manager;
    protected ListView listView;
    protected ListadapterMaster listadapterMaster;
    protected ArrayList<Masterobject> listofmasters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterlist);

        manager = DBmanager.getInstance();
        manager.firstlaunch(getApplicationContext());

        listView = (ListView) findViewById(R.id.masterlist);


        if (savedInstanceState != null){
            listofmasters = savedInstanceState.getParcelableArrayList("masters");
        }
        else {
            listofmasters = manager.getallmasters();
        }

        listadapterMaster = new ListadapterMaster(getApplicationContext(), listofmasters);
        listView.setAdapter(listadapterMaster);

        removelistener();

        clicklistener();

    }

    protected void clicklistener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Masterobject masterlisttoparce = (Masterobject) listView.getItemAtPosition(position);

                Intent intent = new Intent(Masterlist.this, list.class);
                intent.putExtra("masterlistparce", masterlisttoparce);
                startActivity(intent);
            }
        });
    }

    protected void removelistener(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Masterobject master = (Masterobject) listView.getItemAtPosition(position);
              manager.deletemaster(master);
              listadapterMaster.remove(master);
              listadapterMaster.notifyDataSetChanged();
              return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList("masters", listofmasters);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void addmasterlist(View view){
        final EditText editText = (EditText) findViewById(R.id.listinput);
        if (!editText.getText().toString().isEmpty()){
            manager.newmasterlist(editText.getText().toString());
            listofmasters = manager.getallmasters();
            listadapterMaster.notifyDataSetChanged();

            removelistener();
            clicklistener();
            editText.setText("");
            HideKeys();
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
