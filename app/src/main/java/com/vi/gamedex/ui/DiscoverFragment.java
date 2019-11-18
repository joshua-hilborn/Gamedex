package com.vi.gamedex.ui;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

    private Activity activity;

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private GameListViewModel gameListViewModel;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        setHasOptionsMenu(true);
        this.activity = getActivity();

        gameListViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        gameListViewModel.getGameList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> gameList) {
                gameListAdapter.setGameList(gameList);
            }
        });

        gameListViewModel.getCurrentDicoverPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // Send page number to MainActivity
                try{
                    ((PageNumberListener) activity).onPageChanged(integer);
                }catch (ClassCastException cce){
                    cce.printStackTrace();

                }
            }
        });

        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(getContext(),  this);
        recyclerView.setAdapter(gameListAdapter);

        // Only query the api if the discover tab is empty, not on state change
        if (gameListViewModel.getGameList().getValue() == null) {
            gameListViewModel.queryDiscover(getContext());
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_d_refresh){
            gameListViewModel.queryDiscover(getContext());
        }else if (item.getItemId() == R.id.menu_d_prev){
            decrementPage();

        }else if (item.getItemId() == R.id.menu_d_next){
            incrementPage();
        }
        return true;
    }

    private void incrementPage(){
       Integer currentPage = gameListViewModel.getCurrentDicoverPage().getValue();
       if (currentPage != null){
           currentPage++;
           gameListViewModel.getCurrentDicoverPage().setValue(currentPage);
           gameListViewModel.queryDiscover(getContext());
       } else {
           gameListViewModel.getCurrentDicoverPage().setValue(0);
       }

    }

    private void decrementPage(){
        Integer currentPage = gameListViewModel.getCurrentDicoverPage().getValue();
        if (currentPage != null){
            if (currentPage == 0){
                Toast.makeText(getContext(), getString(R.string.toast_first_page), Toast.LENGTH_SHORT).show();
                return;
            }else {
                currentPage--;
                gameListViewModel.getCurrentDicoverPage().setValue(currentPage);
                gameListViewModel.queryDiscover(getContext());
            }
        } else {
            gameListViewModel.getCurrentDicoverPage().setValue(0);
        }

    }


    @Override
    public void onGameClick(int position) {

    }

    public interface PageNumberListener{
        void onPageChanged(int page);
    }



}
