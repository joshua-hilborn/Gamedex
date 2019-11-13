package com.vi.gamedex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vi.gamedex.model.Game;
import com.vi.gamedex.repository.GameListRepository;

import java.util.List;

public class GameListViewModel extends AndroidViewModel {

    private GameListRepository repo = GameListRepository.getInstance();

    private LiveData<List<Game>> gameList;
    private LiveData<List<Game>> searchResultsList;


    public GameListViewModel(@NonNull Application application) {
        super(application);
        gameList = repo.getGameList();
        searchResultsList = repo.getSearchResultsList();
    }

    public LiveData<List<Game>> getGameList() {
        return gameList;
    }

    public LiveData<List<Game>> getSearchResultsList() {
        return searchResultsList;
    }
}
