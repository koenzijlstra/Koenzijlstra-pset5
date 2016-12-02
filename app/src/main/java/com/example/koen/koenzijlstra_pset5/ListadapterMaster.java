package com.example.koen.koenzijlstra_pset5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Koen on 2-12-2016.
 */

class ListadapterMaster extends ArrayAdapter<Masterobject> {

    ListadapterMaster(Context context, ArrayList<Masterobject> masterlists){
        super(context, 0, masterlists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Masterobject masterobj = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem, parent, false);
        }

        TextView listnames = (TextView) convertView.findViewById(R.id.masterlisttitle);
        listnames.setText(masterobj.getName());
        return convertView;
    }
}


