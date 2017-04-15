package com.project.danilopereira.trabalho_final.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danilopereira on 01/04/17.
 */

public class User implements Parcelable{
    private int id;
    private String name;
    private String password;

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        password = in.readString();
    }

    public User(){

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(password);
    }
}
