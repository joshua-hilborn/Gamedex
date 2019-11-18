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
import com.vi.gamedex.repository.GameListRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class IgdbReceiver extends BroadcastReceiver {
    public static final String TAG = "IgdbReceiver";
    public static final String ACTION_RESPONSE = "com.vi.gamedex.intent.action.RESPONSE_RECEIVED";

    private final MutableLiveData<List<Game>> receivedData = new MutableLiveData<>();


    public MutableLiveData<List<Game>> getReceivedData() {
        return receivedData;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null){
            return;
        }

        String resultString = "";
        if (intent.hasExtra(QueryIgdbService.EXTRA_RESPONSE)) {
            resultString = intent.getStringExtra(QueryIgdbService.EXTRA_RESPONSE);
            Log.d(TAG, "onReceive: broadcast recieved");
        }

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Game.class);
        final JsonAdapter<List> gamesJsonAdapter = moshi.adapter(type);

        try {
            if (resultString != null){
                if (resultString.equals("[]")){
                    Toast.makeText(context, context.getString(R.string.toast_no_results), Toast.LENGTH_LONG).show();
                } else {
                    receivedData.postValue(gamesJsonAdapter.fromJson(resultString));
                    Log.d(TAG, "onReceive: posting value to livedata: " + resultString);
                }
            } else {
                //handle null result
                //Log.d(TAG, "onTaskComplete: Null Result");
                Toast.makeText(context, context.getString(R.string.toast_no_results), Toast.LENGTH_LONG).show();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
