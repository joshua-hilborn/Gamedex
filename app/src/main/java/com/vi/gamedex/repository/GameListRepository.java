package com.vi.gamedex.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.database.GameDao;
import com.vi.gamedex.database.GameDatabase;
import com.vi.gamedex.igdb.OkHttpAsyncTask;
import com.vi.gamedex.model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_GAMELIST_FIELDS;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_PAGE_LIMIT;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_ENDPOINT_GAMES;

public class GameListRepository {
    private static final String TAG = "GameListRepository: ";

    private static GameListRepository instance;
    private MutableLiveData<List<Game>> gameList = new MutableLiveData<>();
    private MutableLiveData<List<Game>> searchResultsList = new MutableLiveData<>();
    private LiveData<List<Game>> favoritesList;

    private GameDao gameDao;

    GameListRepository( Application application) {
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        gameDao = gameDatabase.gameDao();
        Log.d(TAG, "GameListRepository: Loading Favorites from db" );
        favoritesList = gameDao.loadAllGameFavorites();
    }

    // SINGLETON
    public static GameListRepository getInstance( Application application) {
        if(instance == null) {
            synchronized (GameListRepository.class) {
                if(instance == null) {
                    instance = new GameListRepository(application);
                }
            }
        }
        return instance;
    }


    public LiveData<List<Game>> getGameList (){
        return gameList;
    }

    public LiveData<List<Game>> getSearchResultsList() {
        return searchResultsList;
    }

    public LiveData<List<Game>> getFavoritesList(){
        return favoritesList;
    }

    public Game loadGameById (int gameId){
        return gameDao.loadGameById(gameId);
    }

    public void addGametoFavorites ( Game game ){
        gameDao.insertGame(game);
    }

    public void deleteGameFromFavorites ( Game game ){
        gameDao.deleteGame(game);
    }


    public void queryIGDBSearch( String searchString){
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        String query = "search \""+ searchString +"\";";
        String limit = "limit " + IGDB_API_PAGE_LIMIT + ";";
        String body = IGDB_API_GAMELIST_FIELDS + " " + query + " " + limit;

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){
                        searchResultsList.postValue(gamesJsonAdapter.fromJson(result));
                    }else{
                        //handle null result
                        Log.d(TAG, "onTaskComplete: Null Result");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).execute(IGDB_ENDPOINT_GAMES, body);

    }

    public void queryIGDBComingSoon(int page){

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;

        int offset = page * IGDB_API_PAGE_LIMIT;
        String body = IGDB_API_GAMELIST_FIELDS +
                "where first_release_date > " + currentTimestamp + ";" +
                "sort first_release_date asc;" +
                "limit " + IGDB_API_PAGE_LIMIT + "; " +
                "offset " + offset + ";"; // add offset 50 to start at page 2

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){
                        gameList.postValue(gamesJsonAdapter.fromJson(result));
                    }else{
                        //handle null result
                        Log.d(TAG, "onTaskComplete: Null Result");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).execute(IGDB_ENDPOINT_GAMES, body);

    }
}
