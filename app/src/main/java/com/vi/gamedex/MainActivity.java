package com.vi.gamedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView jsonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonTextView = findViewById(R.id.tv_json_test);

        //new OkHttpAsyncTask().execute("/games", "fields *;");
        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        Log.d("MainActivity: ", "onCreate: " + currentMillis);

        //fields *; where game.platforms = 48 & date > 1538129354; sort date asc;

        String endpoint = "/release_dates";

        String fields = "fields *;";
        String query = "where game.platforms = 48 & date > " + currentMillis + ";";
        String sort = "sort date desc;";

        String body = fields + " " + query + " " + sort;

        new OkHttpAsyncTask(new OkHttpAsyncTask.OkHttpAsyncTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                jsonTextView.setText(result);
                Log.d("MainActivity: ", "OkHttpCallback: onTaskComplete: " + result);
            }
        }).execute(endpoint, body);

    }




}