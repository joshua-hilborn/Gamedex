package com.vi.gamedex.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements GameListAdapter.OnGameListener {

    public static final String TAG = "MainActivity: ";

    //private TextView jsonTextView;
    private RecyclerView recyclerView;
    private Button searchButton;
    private EditText searchTextBox;
    private List<Game> gamesList;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        searchButton = rootView.findViewById(R.id.btn_search);
        searchTextBox = rootView.findViewById(R.id.et_searchBox);
        recyclerView = rootView.findViewById(R.id.rv_search);

        //AutoFitGridLayoutManager autoFitGridLayoutManager = new AutoFitGridLayoutManager(this,350);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setLayoutManager(autoFitGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        final GameListAdapter gameListAdapter = new GameListAdapter(gamesList, this);
        recyclerView.setAdapter(gameListAdapter);

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);


        //jsonTextView = findViewById(R.id.tv_json_test);

        //new OkHttpAsyncTask().execute("/games", "fields *;");
        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        Log.d(TAG, "onCreate: " + currentMillis);

        //fields *; where game.platforms = 48 & date > 1538129354; sort date asc;

        //String endpoint = "/release_dates";
        //String endpoint = "/games";

        // String fields = "fields *;";
        //String query = "where game.platforms = 48 & date > " + currentMillis + ";";
        //String searchString =  "Final Fantasy";
        // String query = "search \""+ searchString +"\";";
        //String query = "";
        //String sort = "sort date desc;";
        //String sort = "limit 50;";

        //String body2 = "fields *;\nsearch \"Morta\";\nlimit 50;";
        //String body = fields + "\n" + query + "\n" + sort;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endpoint = "/games";
                //String fields = "fields *, cover.*;";
                String fields = "fields *, cover.*, artworks.*, external_games.*, game_modes.*, screenshots.*, platforms.*, genres.*, videos.*;";
                String searchString = searchTextBox.getText().toString();
                //String searchString =  "Final Fantasy";
                String query = "search \""+ searchString +"\";";
                String sort = "limit 50;";
                String body = fields + "\n" + query + "\n" + sort;

                new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
                    @Override
                    public void onTaskComplete(String result) {
                        try{
                            Log.d(TAG, "onTaskComplete: " + result);
                            if (result != null){
                                gamesList = gamesJsonAdapter.fromJson(result);
                                gameListAdapter.setGameList(gamesList);
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
        });


        return rootView;


    }

    @Override
    public void onGameClick(int position) {

    }
}
