package com.vi.gamedex.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DiscoverFragment.PageNumberListener {
    private static final String TAG = "MainActivity: ";

    private FirebaseAnalytics firebaseAnalytics;

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAnalytics();
        setupViews();
        setupAdmob();
        updateWidgets();
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

    private void setupAdmob() {
        // Setup Admob Banner
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
        DateFormat format = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, format.format(new Date()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MainActivity");
        firebaseAnalytics.logEvent("ActivityStart", bundle);
    }

    @Override
    public void onPageChanged(int page) {
        int displayPage = page + 1;
        tabLayout.getTabAt(0).setText(getString(R.string.tab_discover_title) + " (" + displayPage + ")");
    }
}