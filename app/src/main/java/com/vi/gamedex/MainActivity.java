package com.vi.gamedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameListAdapter.OnGameListener {

    //private TextView jsonTextView;
    private RecyclerView recyclerView;
    private List<Game> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final GameListAdapter gameListAdapter = new GameListAdapter(gamesList, this);
        recyclerView.setAdapter(gameListAdapter);

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);


        //jsonTextView = findViewById(R.id.tv_json_test);

        //new OkHttpAsyncTask().execute("/games", "fields *;");
        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        Log.d("MainActivity: ", "onCreate: " + currentMillis);

        //fields *; where game.platforms = 48 & date > 1538129354; sort date asc;

        //String endpoint = "/release_dates";
        String endpoint = "/games";

        String fields = "fields *;";
        //String query = "where game.platforms = 48 & date > " + currentMillis + ";";
        String query = "";
        //String sort = "sort date desc;";
        String sort = "";

        String body = fields + " " + query + " " + sort;

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                try{
                    gamesList = gamesJsonAdapter.fromJson(result);
                    gameListAdapter.setGameList(gamesList);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //jsonTextView.setText(result);
                //Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + result);
                Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + gamesList.size());
                Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + gamesList.get(1).getName());
                Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + gamesList.get(1).getSummary());

            }
        }).execute(endpoint, body);

    }


    @Override
    public void onGameClick(int position) {

    }
}