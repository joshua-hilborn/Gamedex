package com.vi.gamedex.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
public class DiscoverFragment extends Fragment implements GameListAdapter.OnGameListener {
    public static final String TAG = "DiscoverFragment: ";

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private GameListViewModel gameListViewModel;
    private int currentPage = 0;


    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        setHasOptionsMenu(true);

        gameListViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        gameListViewModel.getGameList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {
                gameListAdapter.setGameList(gameList);

            }
        });

        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(getContext(),  this);
        recyclerView.setAdapter(gameListAdapter);

        // Only query the api if the discover tab is empty, not on state change
        if (gameListViewModel.getGameList().getValue() == null) {
            gameListViewModel.queryDiscover(getContext(), currentPage);
        }

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
            gameListViewModel.queryDiscover(getContext(), currentPage);
        }else if (item.getItemId() == R.id.menu_d_prev){
            if (currentPage == 0){
                Toast.makeText(getContext(), "First Page", Toast.LENGTH_SHORT).show();
            }else{
                currentPage--;
                gameListViewModel.queryDiscover(getContext(), currentPage);
            }

        }else if (item.getItemId() == R.id.menu_d_next){
            currentPage++;
            gameListViewModel.queryDiscover(getContext(), currentPage);

        }
        return true;
    }


    @Override
    public void onGameClick(int position) {
        // Detail Activity NYI
        //Toast.makeText(getActivity(), gameListViewModel.getGameList().getValue().get(position).getName() + " Clicked", Toast.LENGTH_SHORT).show();

    }



}
