package com.vi.gamedex.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.igdb.OkHttpAsyncTask;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.model.ReleaseDate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements GameListAdapter.OnGameListener {
    public static final String TAG = "DiscoverFragment: ";

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private List<Game> gameList;


    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        setHasOptionsMenu(true);

        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter( this);
        recyclerView.setAdapter(gameListAdapter);

        queryIGDBComingSoon();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_discover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_d_refresh){
            //gameListAdapter.setGameList(new ArrayList<Game>());
            queryIGDBComingSoon();
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onGameClick(int position) {

    }

    public void queryIGDBComingSoon(){
        Moshi moshi = new Moshi.Builder()
                .build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;
        Log.d(TAG, "onCreate: Current Time Stamp " + currentTimestamp);

        //String endpoint = "/release_dates";
        String endpoint = "/games";
        //String body = "fields *, game.*, game.cover.*, game.artworks.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.videos.*;" +
        String body = "fields *, cover.*, artworks.*, game_modes.*, screenshots.*, platforms.*, genres.*;" +
                "where first_release_date > " + currentTimestamp + ";" +
                "sort date asc;" +
                "limit 50; offset 50;";

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){
                        gameList = gamesJsonAdapter.fromJson(result);
                        gameListAdapter.setGameList(gameList);
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
