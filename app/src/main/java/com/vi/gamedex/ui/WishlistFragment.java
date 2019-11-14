package com.vi.gamedex.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements GameListAdapter.OnGameListener {

    public static final String TAG = "WishlistFragment: ";

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private GameListViewModel gameListViewModel;


    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);
        setHasOptionsMenu(true);

        gameListViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        gameListViewModel.getFavoritesList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {
                gameListAdapter.setGameList(gameList);

            }
        });

        recyclerView = rootView.findViewById(R.id.rv_wishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter( this);
        recyclerView.setAdapter(gameListAdapter);

        //gameListViewModel.queryDiscover(currentPage);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onGameClick(int position) {

    }

    @Override
    public void onFavoritesButtonClick(View view, int position) {

    }

    @Override
    public void onCalendarButtonClick(View view, int position) {

    }
}
