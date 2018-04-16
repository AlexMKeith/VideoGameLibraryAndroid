package com.example.codygividen.videogamelibraryandroid;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VideoGameDao {


    //Allow us to get all video games
    @Query("SELECT * FROM videogame")
    List<VideoGame> getVideoGames();

    //Allows us to add a single game to the list
    @Insert
    void addVideoGame(VideoGame videoGame);

    //Allows us to update the values of an existing game
    @Update
    void updateVideoGame(VideoGame videoGame);

    //Allows us to delete a game from the library
    @Delete
    void deleteVideoGame(VideoGame videoGame);


}
