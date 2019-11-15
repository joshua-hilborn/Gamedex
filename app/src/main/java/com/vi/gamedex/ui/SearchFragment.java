package com.vi.gamedex.ui;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements GameListAdapter.OnGameListener {

    public static final String TAG = "SearchFragment: ";

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private Button searchButton;
    private EditText searchTextBox;
    private GameListViewModel gameListViewModel;


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
        gameListAdapter = new GameListAdapter(getContext(), this);
        recyclerView.setAdapter(gameListAdapter);


        searchTextBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (event.getAction() == KeyEvent.ACTION_DOWN) && ( actionId == KeyEvent.KEYCODE_ENTER )){
                    performSearch();
                    //performSearch();
                    return true;
                }
                return false;
            }
        });



        searchTextBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    performSearch();
                    // Perform action on key press
                    //Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();

            }
        });

        return rootView;

    }

    private void performSearch() {
        String searchString = searchTextBox.getText().toString();
        gameListViewModel.querySearch(searchString);
    }


    @Override
    public void onGameClick(int position) {

    }

}
