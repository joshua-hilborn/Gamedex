package com.vi.gamedex.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.vi.gamedex.model.Game;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class GameDatabase extends RoomDatabase {
    private static final String TAG = "GameDatabase: ";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "gameFavorites";
    private static GameDatabase instance;


    //SINGLETON
    public static GameDatabase getInstance( Context context){
        if (instance == null){
            synchronized (LOCK){
                //Log.d(TAG, "getInstance: Creating new database instance");
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        GameDatabase.class,
                        GameDatabase.DATABASE_NAME)
                        .build();
            }
        }
        //Log.d(TAG, "getInstance: Getting database instance");
        return instance;
    }

    public abstract GameDao gameDao();
}