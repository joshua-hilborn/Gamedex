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
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.util.Date;
import java.util.List;

import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_GAMELIST_FIELDS;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_PAGE_LIMIT;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_ENDPOINT_GAMES;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements GameListAdapter.OnGameListener {
    public static final String TAG = "DiscoverFragment: ";
    private static final float ALPHA_DIMMED = .5f;
    private static final float ALPHA_FULL = 1;
    private static int DISCOVER_TAB = 0;

    private Activity activity;
    private ConnectivityManager connectivity;

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;
    private GameListViewModel gameListViewModel;
    private ProgressBar discoverProgressBar;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    private BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            discoverProgressBar.setVisibility(View.GONE);
            recyclerView.setAlpha(ALPHA_FULL);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        this.activity = getActivity();
        discoverProgressBar = rootView.findViewById(R.id.pb_discover);
        connectivity = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        setHasOptionsMenu(true);
        setupViewModel();
        setupRecyclerView(rootView);
        queryIfEmpty();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerProgressReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterProgressReceiver();
    }

    private void registerProgressReceiver() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter(IgdbReceiver.ACTION_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        localBroadcastManager.registerReceiver(progressReceiver, filter);
    }

    private void unregisterProgressReceiver(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.unregisterReceiver(progressReceiver);
    }

    // Only query the api if the discover tab is empty, not on state change
    private void queryIfEmpty() {
        if (gameListViewModel.getGameList().getValue() == null) {
            queryIGDBComingSoon();
        }
    }

    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_discover);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gameListAdapter = new GameListAdapter(getContext(),  this);
        recyclerView.setAdapter(gameListAdapter);
    }

    private void setupViewModel() {
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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_d_refresh){
            queryIGDBComingSoon();
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
           queryIGDBComingSoon();
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
                queryIGDBComingSoon();
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

    private void queryIGDBComingSoon(){
        Integer page = gameListViewModel.getCurrentDicoverPage().getValue();
        if (page == null){
            page = 0;
        }
        if ( !isConnectedToInternet() ){
            Toast.makeText(getContext(), getString(R.string.toast_no_network), Toast.LENGTH_LONG).show();
            return;
        }

        discoverProgressBar.setVisibility(View.VISIBLE);
        recyclerView.setAlpha(ALPHA_DIMMED);

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        long currentTimestamp = currentMillis / 1000;

        int offset = page * IGDB_API_PAGE_LIMIT;
        String body = IGDB_API_GAMELIST_FIELDS +
            IgdbUtilities.IGDB_WHERE_RELEASE_DATE_GREATER + currentTimestamp + IgdbUtilities.IGDB_SEMICOLON +
                IgdbUtilities.IGDB_SORT_RELEASE_DATE_ASC +
                IgdbUtilities.IGDB_API_KEYWORD_LIMIT + IGDB_API_PAGE_LIMIT + "; " +
                IgdbUtilities.IGDB_API_KEYWORD_OFFSET + offset + IgdbUtilities.IGDB_SEMICOLON; // add offset 50 to start at page 2

        Intent queryIntent = new Intent(getContext(), QueryIgdbService.class);
        queryIntent.putExtra(QueryIgdbService.EXTRA_ENDPOINT, IGDB_ENDPOINT_GAMES);
        queryIntent.putExtra(QueryIgdbService.EXTRA_BODY, body);
        queryIntent.putExtra(QueryIgdbService.EXTRA_REQUESTING_TAB, DISCOVER_TAB);
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
