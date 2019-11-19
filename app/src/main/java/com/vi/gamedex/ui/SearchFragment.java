package com.vi.gamedex.ui;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vi.gamedex.R;
import com.vi.gamedex.adapter.GameListAdapter;
import com.vi.gamedex.igdb.IgdbReceiver;
import com.vi.gamedex.igdb.IgdbUtilities;
import com.vi.gamedex.igdb.QueryIgdbService;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.repository.GameListRepository;
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.util.List;

import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_GAMELIST_FIELDS;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_PAGE_LIMIT;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_ENDPOINT_GAMES;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements GameListAdapter.OnGameListener {

    public static final String TAG = "SearchFragment: ";

    private Activity activity;
    private IgdbReceiver igdbReceiver;
    private static int SEARCH_TAB = 1;
    private ConnectivityManager connectivity;

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private GameListViewModel gameListViewModel;
    private ProgressBar searchProgressBar;


    public SearchFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            searchProgressBar.setVisibility(View.GONE);
            recyclerView.setAlpha(1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        igdbReceiver = new IgdbReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter(IgdbReceiver.ACTION_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        localBroadcastManager.registerReceiver(igdbReceiver, filter);
        localBroadcastManager.registerReceiver(progressReceiver, filter);
        GameListRepository.getInstance(activity.getApplication()).addSearchSource(igdbReceiver.getReceivedSearchData());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchProgressBar = rootView.findViewById(R.id.pb_search);
        this.activity = getActivity();
        connectivity = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

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
                queryIGDBSearch(query);
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

    private void queryIGDBSearch(String searchString){
        if ( !isConnectedToInternet()){
            Toast.makeText(getContext(), getString(R.string.toast_no_network), Toast.LENGTH_LONG).show();
            return;
        }

        searchProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setAlpha(.5f);

        String query = IgdbUtilities.IGDB_API_KEYWORD_SEARCH_OPEN + searchString + IgdbUtilities.IGDB_API_KEYWORD_SEARCH_CLOSE;
        String limit = IgdbUtilities.IGDB_API_KEYWORD_LIMIT + IGDB_API_PAGE_LIMIT + IgdbUtilities.IGDB_SEMICOLON;
        String body = IGDB_API_GAMELIST_FIELDS + " " + query + " " + limit;

        Intent queryIntent = new Intent(getContext(), QueryIgdbService.class);
        queryIntent.putExtra(QueryIgdbService.EXTRA_ENDPOINT, IGDB_ENDPOINT_GAMES);
        queryIntent.putExtra(QueryIgdbService.EXTRA_BODY, body);
        queryIntent.putExtra(QueryIgdbService.EXTRA_REQUESTING_TAB, SEARCH_TAB);
        activity.startService(queryIntent);
    }

    private boolean isConnectedToInternet(){
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

}
