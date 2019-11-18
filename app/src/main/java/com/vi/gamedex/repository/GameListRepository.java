package com.vi.gamedex.repository;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.vi.gamedex.database.GameDao;
import com.vi.gamedex.database.GameDatabase;
import com.vi.gamedex.model.Game;

import java.util.List;

public class GameListRepository {
    private static final String TAG = "GameListRepository: ";

    private static GameListRepository instance;
    private ConnectivityManager connectivity;
    private MediatorLiveData<List<Game>> gameList = new MediatorLiveData<>();
    private MediatorLiveData<List<Game>> searchResultsList = new MediatorLiveData<>();
    private LiveData<List<Game>> favoritesList;

    private GameDao gameDao;

    private GameListRepository( Application application) {
        connectivity = (ConnectivityManager) application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        GameDatabase gameDatabase = GameDatabase.getInstance(application);
        gameDao = gameDatabase.gameDao();
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


    public void addDiscoverSource(LiveData<List<Game>> data) {
        gameList.addSource(data, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> value) {
                gameList.setValue(value);
            }
        });
    }

    public void removeDiscoverSource(LiveData<List<Game>> data) {
        gameList.removeSource(data);
    }


    public void addSearchSource(LiveData<List<Game>> data) {
        searchResultsList.addSource(data, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> value) {
                searchResultsList.setValue(value);
            }
        });
    }

    public void removeSearchSource(LiveData<List<Game>> data) {
        searchResultsList.removeSource(data);
    }
}
