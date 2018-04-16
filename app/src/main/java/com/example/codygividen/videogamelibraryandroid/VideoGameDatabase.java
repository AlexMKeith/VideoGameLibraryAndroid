package com.example.codygividen.videogamelibraryandroid;
@Database(version = 1, entities = VideoGame.class)
@TypeConverters(DateConverter.class)
public abstract class VideoGameDatabase extends RoomDatabase{


    public adstract VideoGameDao videoGameDao();

}
