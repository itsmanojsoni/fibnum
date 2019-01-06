package com.example.manojsoni.logitechinterview.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class Movie {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieid")
    public String id;


    @ColumnInfo(name = "movietitle")
    public String title;

    @ColumnInfo(name = "movieimage")
    public String image;


    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }


}
