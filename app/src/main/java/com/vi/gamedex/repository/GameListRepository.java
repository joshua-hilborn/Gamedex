package com.vi.gamedex.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.igdb.OkHttpAsyncTask;
import com.vi.gamedex.model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class GameListRepository {
    private static final String TAG = "GameListRepository: ";

    private static GameListRepository instance;
    private MutableLiveData<List<Game>> gameList = new MutableLiveData<>();
    private MutableLiveData<List<Game>> searchResultsList = new MutableLiveData<>();

    // SINGLETON
    public static GameListRepository getInstance() {
        if(instance == null) {
            synchronized (GameListRepository.class) {
                if(instance == null) {
                    instance = new GameListRepository();
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

    public void queryIGDBSearch( String searchString){
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        String endpoint = "/games";
        String fields = "fields *, cover.*, artworks.*, screenshots.*, platforms.*, genres.*;";
        String query = "search \""+ searchString +"\";";
        String sort = "limit 50;";
        String body = fields + "\n" + query + "\n" + sort;

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){
                        searchResultsList.postValue(gamesJsonAdapter.fromJson(result));
                    }else{
                        //handle null result
                        Log.d(TAG, "onTaskComplete: Null Result, operation Bluto'd");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).execute(endpoint, body);

    }

    public void queryIGDBComingSoon(int page){


        //final List<Game> resultsList;
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;
        Log.d(TAG, "onCreate: Current Time Stamp " + currentTimestamp);

        String endpoint = "/games";
        int offset = page * 50;
        String body = "fields *, cover.*, artworks.*, screenshots.*, platforms.*, genres.*;" +
                "where first_release_date > " + currentTimestamp + ";" +
                "sort date asc;" +
                "limit 50; " +
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
                        Log.d(TAG, "onTaskComplete: Null Result, operation Bluto'd");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).execute(endpoint, body);

    }
}
