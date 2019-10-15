package com.vi.gamedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private TextView mJsonText;
    private String mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJsonText = findViewById(R.id.tv_json_test);

        //new OkHttpAsyncTask().execute("/games", "fields *;");
        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        Log.d("MainActivity: ", "onCreate: " + currentMillis);

        //fields *; where game.platforms = 48 & date > 1538129354; sort date asc;

        //new OkHttpAsyncTask().execute("/genres", "fields *; where id = (8,9,11);");

        String endpoint = "/release_dates";

        String fields = "fields *;";
        String query = "where game.platforms = 48 & date > " + currentMillis + ";";
        String sort = "sort date desc;";

        String body = fields + " " + query + " " + sort;

        new OkHttpAsyncTask().execute(endpoint, body);

    }


    // Async Task used for rubric requirement, OKHTTP can do async too though
    public class OkHttpAsyncTask extends AsyncTask<String, Void, String> {

        private static final String IGDB_API_KEY_HEADER = "user-key";
        private static final String IGDB_API_KEY = "API KEY HERE";
        private static final String IGDB_BASE_URL = "https://api-v3.igdb.com";

        private final OkHttpClient okHttpClient = new OkHttpClient();

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
            mResponse = result;
            Log.d("OkHttpAsyncTask: ", "onPostExecute: " + result);
            mJsonText.setText(mResponse);
        }
    }

}
