package com.example.koen.koenzijlstra_pset5;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.CompoundButton;

import java.util.ArrayList;

class ListadapterTodo extends ArrayAdapter<Todo_object> {
    DBmanager dBmanager;
    ListadapterTodo(Context context, ArrayList<Todo_object> todos){
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        dBmanager = dBmanager.getInstance();
        final Todo_object todo = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todoitem, parent, false);
        }

        TextView todoname = (TextView) convertView.findViewById(R.id.todoname);
        CheckBox todocheckbox = (CheckBox) convertView.findViewById(R.id.checkBox);


        // wat gaat hier fout?
        todocheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dBmanager.whenchecked(todo, isChecked);
            }
        });

        if (todo != null) {
            todoname.setText(todo.getTodo());
            todocheckbox.setChecked(todo.getChecked());
        }

        return convertView;
    }
}
