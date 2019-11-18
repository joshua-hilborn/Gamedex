package com.vi.gamedex.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
    private GameListViewModel gameListViewModel;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        setupViewModel();

        setupRecyclerView(rootView);

        return rootView;

    }

    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(getContext(), this);
        recyclerView.setAdapter(gameListAdapter);
    }

    private void setupViewModel() {
        gameListViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        gameListViewModel.getSearchResultsList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {
                gameListAdapter.setGameList(gameList);

            }
        });
    }

    private void performSearch(String searchString) {
        gameListViewModel.querySearch(getContext(), searchString);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.menu_s_search);
        final SearchView searchTextBox = (SearchView) searchItem.getActionView();

        searchTextBox.setQueryHint(getString(R.string.search_game_hint));

        // Use these to expand the search bar and close the keyboard when tab is switched to, not sure if i like this yet.
        //searchTextBox.setIconified(false);
        //searchTextBox.clearFocus();
        searchTextBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                // First call clears text, second call returns to icon
                searchTextBox.setIconified(true);
                searchTextBox.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onGameClick(int position) {

    }

}
