package com.example.codygividen.videogamelibraryandroid;

import android.app.Application;

public class VideoGameApplication extends Application {

    private VideoGameDatabase database;
    public static String DATABASE_NAME = "video_game_database";

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), VideoGameDatabase
                .class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }
    public VideoGameDatabase getDatabase(){
        return database;
    }
}
