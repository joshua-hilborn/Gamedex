package com.vi.gamedex.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import com.vi.gamedex.igdb.OkHttpAsyncTask;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.repository.GameListRepository;
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements GameListAdapter.OnGameListener {

    public static final String TAG = "SearchFragment: ";

    //private TextView jsonTextView;
    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private Button searchButton;
    private EditText searchTextBox;
    private GameListViewModel gameListViewModel;
    //private List<Game> gamesList;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        gameListViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        gameListViewModel.getSearchResultsList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {
                gameListAdapter.setGameList(gameList);

            }
        });

        searchButton = rootView.findViewById(R.id.btn_search);
        searchTextBox = rootView.findViewById(R.id.et_searchBox);
        recyclerView = rootView.findViewById(R.id.rv_search);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(this);
        recyclerView.setAdapter(gameListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchTextBox.getText().toString();
                GameListRepository.getInstance().queryIGDBSearch(searchString);

            }
        });




        return rootView;


    }

    @Override
    public void onGameClick(int position) {

    }




}
