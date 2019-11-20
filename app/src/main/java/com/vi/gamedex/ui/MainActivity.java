package com.vi.gamedex.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vi.gamedex.R;
import com.vi.gamedex.adapter.PageAdapter;
import com.vi.gamedex.igdb.IgdbReceiver;
import com.vi.gamedex.repository.GameListRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DiscoverFragment.PageNumberListener {
    private static final String TAG = "MainActivity: ";
    public static final String ACTIVITY_START = "ActivityStart";
    public static final String MAIN_ACTIVITY = "MainActivity";
    public static final String ANALYTICS_TIMESTAMP_FORMAT = "ddMMyyyyhhmmss";
    public static final String KEY_RECIVER_REGISTERED = "isReceiverRegistered";

    private FirebaseAnalytics firebaseAnalytics;
    private IgdbReceiver igdbReceiver;
    private boolean recieverRegistered = false;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    AdView adView;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(KEY_RECIVER_REGISTERED, recieverRegistered);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            if (savedInstanceState.containsKey(KEY_RECIVER_REGISTERED)){
                recieverRegistered = savedInstanceState.getBoolean(KEY_RECIVER_REGISTERED);
            }
        }
        setContentView(R.layout.activity_main);
        setupAnalytics();
        setupViews();
        setupAdmob();
        updateWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerIgdbReceiver();
        recieverRegistered = true;
    }

    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tl_main);
        viewPager = findViewById(R.id.vp_main);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        // Stop Tabs from refreshing by navigating to different tab, cache them
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        // Sync tabs with viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    private void updateWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CountdownWidget.class));
        CountdownWidget.updateCountdownWidgets(this, appWidgetManager, appWidgetIds);
    }

    // Setup Admob Banner
    private void setupAdmob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        adView = findViewById(R.id.av_main);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void setupAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        DateFormat format = new SimpleDateFormat(ANALYTICS_TIMESTAMP_FORMAT, Locale.getDefault());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, format.format(new Date()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, MAIN_ACTIVITY);
        firebaseAnalytics.logEvent(ACTIVITY_START, bundle);
    }

    private void registerIgdbReceiver() {
        if (!recieverRegistered) {
            igdbReceiver = new IgdbReceiver();
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            IntentFilter filter = new IntentFilter(IgdbReceiver.ACTION_RESPONSE);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            localBroadcastManager.registerReceiver(igdbReceiver, filter);
            GameListRepository.getInstance(getApplication()).addDiscoverSource(igdbReceiver.getReceivedData());
            GameListRepository.getInstance(getApplication()).addSearchSource(igdbReceiver.getReceivedSearchData());
        }
    }

    private void unregisterIgdbReceiver(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(igdbReceiver);
        GameListRepository.getInstance(getApplication()).removeDiscoverSource(igdbReceiver.getReceivedData());
        GameListRepository.getInstance(getApplication()).removeSearchSource(igdbReceiver.getReceivedSearchData());
    }

    @Override
    public void onPageChanged(int page) {
        int displayPage = page + 1;
        tabLayout.getTabAt(0).setText(getString(R.string.tab_discover_title) + getString(R.string.tab_discover_title_open_parenthesis) + displayPage + getString(R.string.tab_discover_title_close_parenthesis));
    }
}