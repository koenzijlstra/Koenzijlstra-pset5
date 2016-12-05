package com.example.koen.koenzijlstra_pset5;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;

/**
 * Created by Koen on 2-12-2016.
 */

class Todo_object implements Parcelable {
    private Integer master;
    private Integer id;
    private String todo;
    private boolean checked;

    Todo_object(Integer id, Integer master, String todo, Boolean checked){
        this.id = id;
        this.master = master;
        this.todo = todo;
        this.checked = checked;
    }

    public String getTodo(){
        return this.todo;
    }

    public Integer getId(){
        return this.id;
    }

    void setChecked (Boolean checked){
        this.checked = checked;
    }

    Boolean getChecked(){
        return this.checked;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.master);
        dest.writeValue(this.todo);
        dest.writeValue(this.checked);
    }



    private Todo_object(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.master = (Integer) in.readValue(Integer.class.getClassLoader());
        this.todo = in.readString();
        this.checked = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Todo_object> CREATOR = new Parcelable.Creator<Todo_object>() {
        @Override
        public Todo_object createFromParcel(Parcel source) {
            return new Todo_object(source);
        }

        @Override
        public Todo_object[] newArray(int size) {
            return new Todo_object[size];
        }
    };
}
