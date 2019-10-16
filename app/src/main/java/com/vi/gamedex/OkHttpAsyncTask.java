package com.vi.gamedex;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

// Async Task used for rubric requirement, OKHTTP can do async too though
public class OkHttpAsyncTask extends AsyncTask<String, Void, String> {

    private static final String IGDB_API_KEY_HEADER = "user-key";
    private static final String IGDB_API_KEY = "18611d16bbd6655f41e96f8aacda7d33";
    private static final String IGDB_BASE_URL = "https://api-v3.igdb.com";

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private OkHttpAsyncTaskCallback okHttpAsyncTaskCallback;

    OkHttpAsyncTask (OkHttpAsyncTaskCallback callback){
        this.okHttpAsyncTaskCallback = callback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String endpoint = strings[0];
        String bodyString = strings[1];

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(bodyString, mediaType);

        Request request = new Request.Builder()
                .url(IGDB_BASE_URL + endpoint)
                //.header("Accept", "application/json")
                .header(IGDB_API_KEY_HEADER, IGDB_API_KEY)
                .post(body)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null){
                return responseBody.string();
            }else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        // super.onPostExecute(result);
        //mResponse = result;
        okHttpAsyncTaskCallback.onTaskComplete(result);
        Log.d("OkHttpAsyncTask: ", "onPostExecute: " + result);
        //mJsonText.setText(mResponse);
    }

    public interface OkHttpAsyncTaskCallback {
        void onTaskComplete (String result);
    }
}
