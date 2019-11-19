package com.vi.gamedex.igdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.vi.gamedex.R;
import com.vi.gamedex.model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class IgdbReceiver extends BroadcastReceiver {
    public static final String TAG = "IgdbReceiver";
    public static final String ACTION_RESPONSE = "com.vi.gamedex.intent.action.RESPONSE_RECEIVED";
    public static final String EMPTY_RESULT_BRACKETS = "[]";

    private final MutableLiveData<List<Game>> receivedData = new MutableLiveData<>();
    private final MutableLiveData<List<Game>> receivedSearchData = new MutableLiveData<>();


    public MutableLiveData<List<Game>> getReceivedData() {
        return receivedData;
    }

    public MutableLiveData<List<Game>> getReceivedSearchData() {
        return receivedSearchData;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null){
            return;
        }

        String resultString = "";
        if (intent.hasExtra(QueryIgdbService.EXTRA_RESPONSE)) {
            resultString = intent.getStringExtra(QueryIgdbService.EXTRA_RESPONSE);
            //Log.d(TAG, "onReceive: broadcast received");
        }

        int requestingTab = 0;
        if (intent.hasExtra(QueryIgdbService.EXTRA_REQUESTING_TAB)) {
            requestingTab = intent.getIntExtra(QueryIgdbService.EXTRA_REQUESTING_TAB, 0);
            //Log.d(TAG, "onReceive: broadcast received");
        }

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        try {
            if (resultString != null){
                if (resultString.equals(EMPTY_RESULT_BRACKETS)){
                    //Log.d(TAG, "onReceive: no result? " + resultString);
                    Toast.makeText(context, context.getString(R.string.toast_no_results), Toast.LENGTH_LONG).show();
                } else {
                    if (requestingTab == 0 ) {
                        receivedData.postValue(gamesJsonAdapter.fromJson(resultString));
                        Log.d(TAG, "onReceive: posting to Discover Tab");
                    }else if ( requestingTab == 1 ) {
                        receivedSearchData.postValue(gamesJsonAdapter.fromJson(resultString));
                        Log.d(TAG, "onReceive: posting to Search Tab");
                    }else {
                        Log.d(TAG, "onReceive: no tab number processed");
                    }
                    //Log.d(TAG, "onReceive: posting value to livedata: " + resultString);
                }
            } else {
                //handle null result
                //Log.d(TAG, "onReceive: Null result? " + resultString );
                Toast.makeText(context, context.getString(R.string.toast_no_results), Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
