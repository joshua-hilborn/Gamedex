package com.vi.gamedex.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.vi.gamedex.AppExecutors;
import com.vi.gamedex.R;
import com.vi.gamedex.database.GameDatabase;
import com.vi.gamedex.model.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 */
public class CountdownWidget extends AppWidgetProvider {
    public static final String TAG = "CountdownWidget: ";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);

        new AsyncTask<Context, Void, List<Game>>(){

            @Override
            protected List<Game> doInBackground(Context... contexts) {
                List<Game> games = null;
                GameDatabase gameDatabase = GameDatabase.getInstance(contexts[0]);
                games = gameDatabase.gameDao().loadFavoritesListForWidget();
                return games;
            }

            @Override
            protected void onPostExecute(List<Game> gameList) {
                super.onPostExecute(gameList);
                String widgetText = "";
                Date currenDate = new Date();
                if (gameList != null && gameList.size() > 0){

                    for (int i = 0; i < gameList.size(); i++ ){
                        int releaseDateTimeStamp = gameList.get(i).getFirstReleaseDate();
                        Date releaseDate = new Date( (long) releaseDateTimeStamp * 1000 );

                        if (releaseDate.getTime() == 0){
                            widgetText = widgetText + (i+1) + ") " + gameList.get(i).getName() + "\n\t" +
                                    context.getString(R.string.widget_release_unknown) + "\n";
                            continue;
                        }

                        long diff = releaseDate.getTime() - currenDate.getTime();
                        long days = 1 + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                        if (days <=1 ){
                            widgetText = widgetText + (i+1) + ") " + gameList.get(i).getName() + "\n\t" +
                                    context.getString(R.string.widget_out_now) + "\n";
                        }else {
                            widgetText = widgetText + (i+1) + ") " + gameList.get(i).getName() + "\n\t" +
                                    days + context.getString(R.string.widget_unit_days) +"\n";
                        }
                    }

                } else {
                    widgetText = context.getString(R.string.widget_no_favorites);

                }
                views.setTextViewText(R.id.appwidget_text, widgetText);

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }.execute(context);
    }

    public static void updateCountdownWidgets (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

