package com.vi.gamedex.igdb;

import android.os.AsyncTask;

import com.vi.gamedex.BuildConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_API_KEY_HEADER;
import static com.vi.gamedex.igdb.IgdbUtilities.IGDB_BASE_URL;

// Async Task used for rubric requirement, OKHTTP can do async too though
public class OkHttpAsyncTask extends AsyncTask<String, Void, String> {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpAsyncTaskCallback okHttpAsyncTaskCallback;

    public OkHttpAsyncTask(OkHttpAsyncTaskCallback callback){
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
                .header(IGDB_API_KEY_HEADER, BuildConfig.IGDB_API_KEY)
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
        okHttpAsyncTaskCallback.onTaskComplete(result);
    }

    public interface OkHttpAsyncTaskCallback {
        void onTaskComplete (String result);
    }
}
