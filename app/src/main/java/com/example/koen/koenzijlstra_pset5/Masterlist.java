package com.example.koen.koenzijlstra_pset5;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        // todo lable van arraylist
        if (savedInstanceState != null){
            listofmasters = savedInstanceState.getParcelableArrayList("");
        }
        else {
            listofmasters = manager.getmasters();
        }

        listadapterMaster = new ListadapterMaster(getApplicationContext(), listofmasters);
        listView.setAdapter(listadapterMaster);

        clicklistener();
        // removelistener
    }

    // wat gaat hier nog fout, ging ergens anders ook fout
    protected void clicklistener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onclick(AdapterView<?> parent, View view, int position, long id){
                Masterobject masterlist = (Masterobject) listView.getItemAtPosition(position);
                Intent intent = new Intent(Masterlist.this, list.class);
                intent.putExtra("masterlist", (Parcelable) masterlist);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void removelistener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

        });
    }
}
