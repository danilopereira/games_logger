package com.project.danilopereira.trabalho_final.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danilopereira on 10/04/17.
 */

public class Game implements Parcelable{

    private int id;
    private String name;
    private Category category;
    private String cover;

    public Game(){

    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    protected Game(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readParcelable(Category.class.getClassLoader());
        cover = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeParcelable(category, flags);
        dest.writeString(cover);
    }
}
