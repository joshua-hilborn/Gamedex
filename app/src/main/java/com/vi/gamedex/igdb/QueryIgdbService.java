package com.vi.gamedex.igdb;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.vi.gamedex.BuildConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class QueryIgdbService extends IntentService {
    public static final String TAG = "QueryIgdbService";

    public static final String EXTRA_ENDPOINT = "requestEndpoint";
    public static final String EXTRA_BODY = "requestBody";
    public static final String EXTRA_RESPONSE = "responseJson";

    private final OkHttpClient okHttpClient = new OkHttpClient();


    public QueryIgdbService(){
        super("QueryIgdbService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null){
            return;
        }
        String endpoint = "";
        if (intent.hasExtra(EXTRA_ENDPOINT)) {
            endpoint = intent.getStringExtra(EXTRA_ENDPOINT);
            Log.d(TAG, "onHandleIntent: endpoint: " +  endpoint);
        }

        String bodyString = "";
        if (intent.hasExtra(EXTRA_BODY)) {
            bodyString = intent.getStringExtra(EXTRA_BODY);
            Log.d(TAG, "onHandleIntent: bodyString: " + bodyString);
        }

        String responseString = "";

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(bodyString, mediaType);
        Request request = new Request.Builder()
                .url(IgdbUtilities.IGDB_BASE_URL + endpoint)
                //.header("Accept", "application/json")
                .header(IgdbUtilities.IGDB_API_KEY_HEADER, BuildConfig.IGDB_API_KEY)
                .post(body)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null){
                responseString = responseBody.string();
                Log.d(TAG, "onHandleIntent: responseString: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(IgdbReceiver.ACTION_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(EXTRA_RESPONSE, responseString);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(broadcastIntent);
        Log.d(TAG, "onHandleIntent: Broadcast Sent");

    }
}
