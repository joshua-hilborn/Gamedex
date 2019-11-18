package com.vi.gamedex.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vi.gamedex.model.Game;
import com.vi.gamedex.repository.GameListRepository;

import java.util.List;

public class GameListViewModel extends AndroidViewModel {
    private static final String TAG = "GameListViewModel: ";

    private GameListRepository repo;

    private LiveData<List<Game>> gameList;
    private LiveData<List<Game>> searchResultsList;
    private LiveData<List<Game>> favoritesList;

    private MutableLiveData<Integer> currentDicoverPage = new MutableLiveData<>(0);


    public GameListViewModel(@NonNull Application application) {
        super(application);
        repo = GameListRepository.getInstance(application);
        gameList = repo.getGameList();
        searchResultsList = repo.getSearchResultsList();
        favoritesList = repo.getFavoritesList();

    }

    public MutableLiveData<Integer> getCurrentDicoverPage(){
        return currentDicoverPage;
    }

    public LiveData<List<Game>> getGameList() {
        return gameList;
    }

    public LiveData<List<Game>> getSearchResultsList() {
        return searchResultsList;
    }

    public LiveData<List<Game>> getFavoritesList() { return favoritesList; }

}
