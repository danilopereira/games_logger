package com.project.danilopereira.trabalho_final.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danilopereira on 10/04/17.
 */

public class Category implements Parcelable {


    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Category(Parcel in) {
    }

    public Category(){

    }

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
