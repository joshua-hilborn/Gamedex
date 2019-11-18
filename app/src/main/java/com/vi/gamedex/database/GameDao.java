package com.vi.gamedex.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vi.gamedex.model.Game;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM gameFavorites ORDER BY id")
    LiveData<List<Game>> loadAllGameFavorites();

    @Query("SELECT * FROM gameFavorites ORDER BY id")
    List<Game> loadFavoritesListForWidget();

    @Insert
    void insertGame (Game game);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGame (Game game);

    @Delete
    void deleteGame (Game game);

    @Query("SELECT * FROM gameFavorites WHERE id = :id")
    Game loadGameById(int id);
}
