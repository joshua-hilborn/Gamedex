package com.vi.gamedex.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
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
    List<Game> gameList;


    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        //List<ReleaseDate> releaseDates;

        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final GameListAdapter gameListAdapter = new GameListAdapter(gameList, this);
        recyclerView.setAdapter(gameListAdapter);

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, ReleaseDate.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;
        Log.d(TAG, "onCreate: " + currentMillis);

        /*
        fields *, game.*, game.cover.*, game.artworks.*, game.external_games.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.similar_games.*, game.videos.*, game.involved_companies.*;
where date > 1538129354;
sort date asc;
         */



        String endpoint = "/release_dates";
        String body = "fields *, game.*, game.cover.*, game.artworks.*, game.external_games.*, game.game_modes.*, game.screenshots.*, game.platforms.*, game.genres.*, game.videos.*;\n" +
                "where date > 1538129354;\n" +
                "sort date asc;\n" +
                "limit 50;";

        //String fields = "fields *, cover.*, artworks.*, external_games.*, game_modes.*, screenshots.*, platforms.*, genres.*, videos.*;";
        //String searchString = searchTextBox.getText().toString();

        //String searchString =  "Final Fantasy";
        //String query = "search \""+ searchString +"\";";

        //String sort = "limit 50;";
        //String body = fields + "\n" + query + "\n" + sort;

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    Log.d(TAG, "onTaskComplete: " + result);
                    if (result != null){

                        List<ReleaseDate> releaseDates = gamesJsonAdapter.fromJson(result);
                        List<Game> upcomingList = new ArrayList<Game>();
                        for (ReleaseDate releaseDate : releaseDates){
                            upcomingList.add(releaseDate.getGame());
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




        return rootView;
    }

    @Override
    public void onGameClick(int position) {

    }
}
