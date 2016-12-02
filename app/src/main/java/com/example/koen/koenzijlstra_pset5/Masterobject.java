package com.example.koen.koenzijlstra_pset5;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Koen on 2-12-2016.
 */

public class Masterobject implements Parcelable {
    private String name;
    private Integer id;

    Masterobject(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    // log
    public void masterobjectlog(){
        Log.d("NAME", this.name);
        Log.d("ID", this.id.toString());
    }





    // generated to make parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Masterobject() {
    }

    protected Masterobject(Parcel in) {
    }

    public static final Parcelable.Creator<Masterobject> CREATOR = new Parcelable.Creator<Masterobject>() {
        @Override
        public Masterobject createFromParcel(Parcel source) {
            return new Masterobject(source);
        }

        @Override
        public Masterobject[] newArray(int size) {
            return new Masterobject[size];
        }
    };
}