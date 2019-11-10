package com.vi.gamedex.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
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

    RecyclerView recyclerView;
    GameListAdapter gameListAdapter;
    List<Game> gameList;


    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        setHasOptionsMenu(true);
        //List<ReleaseDate> releaseDates;

        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(gameList, this);
        recyclerView.setAdapter(gameListAdapter);

        queryIGDBComingSoon();



        /*
        fields *, game.*, game.cover.*, game.artworks.*, game.external_games.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.similar_games.*, game.videos.*, game.involved_companies.*;
where date > 1538129354;
sort date asc;
         */





        //String fields = "fields *, cover.*, artworks.*, external_games.*, game_modes.*, screenshots.*, platforms.*, genres.*, videos.*;";
        //String searchString = searchTextBox.getText().toString();

        //String searchString =  "Final Fantasy";
        //String query = "search \""+ searchString +"\";";

        //String sort = "limit 50;";
        //String body = fields + "\n" + query + "\n" + sort;






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
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, ReleaseDate.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;
        Log.d(TAG, "onCreate: Current Time Stamp " + currentTimestamp);

        String endpoint = "/release_dates";
        String body = "fields *, game.*, game.cover.*, game.artworks.*, game.external_games.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.videos.*;\n" +
                "where date > " + currentTimestamp + ";\n" +
                "sort date asc;\n" +
                "limit 50;";

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){

                        List<ReleaseDate> releaseDates = gamesJsonAdapter.fromJson(result);
                        List<Game> upcomingList = new ArrayList<Game>();
                        List<Integer> uniqueIds = new ArrayList<Integer>();
                        for (ReleaseDate releaseDate : releaseDates){
                            if ( !uniqueIds.contains(releaseDate.getGame().getId())  ){
                                upcomingList.add(releaseDate.getGame());
                                uniqueIds.add(releaseDate.getGame().getId());
                            }

                            //gameList.add(releaseDate.getGame());
                        }
                        //gameList = gamesJsonAdapter.fromJson(result);

                        gameListAdapter.setGameList(upcomingList);
                    }else{
                        //handle null result
                        Log.d(TAG, "onTaskComplete: Null Result, operation Bluto'd");
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

                //jsonTextView.setText(result);
                //Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + result);
                //Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + gamesList.size());

            }
        }).execute(endpoint, body);

    }
}
