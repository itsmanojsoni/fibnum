package com.example.manojsoni.logitechinterview.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.manojsoni.logitechinterview.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {


    private static MovieDatabase INSTANCE;

    private static final Object sLock = new Object();

    public abstract MovieDao movieDao();


    public static MovieDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, "Sample.db")
                        .build();
            }
            return INSTANCE;
        }
    }


}
