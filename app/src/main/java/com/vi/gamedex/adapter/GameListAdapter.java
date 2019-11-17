package com.vi.gamedex.adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vi.gamedex.AppExecutors;
import com.vi.gamedex.R;
import com.vi.gamedex.database.GameDatabase;
import com.vi.gamedex.igdb.IgdbUtilities;
import com.vi.gamedex.model.Artwork;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.model.Genre;
import com.vi.gamedex.model.Platform;
import com.vi.gamedex.model.Screenshot;
import com.vi.gamedex.ui.CountdownWidget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {

    private static final String TAG = "GameListAdapter: ";
    private Context context;
    private List<Game> gameList;
    private OnGameListener onGameListener;

    public GameListAdapter(Context context, OnGameListener listener) {
        this.context = context;
        this.onGameListener = listener;
    }

    public GameListAdapter(Context context, OnGameListener listener, List<Game> gList) {
        this.context = context;
        this.gameList = gList;
        this.onGameListener = listener;
    }

    public void setGameList (List<Game> glist){
        this.gameList = glist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        return new GameViewHolder(view, onGameListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String gameName = gameList.get(position).getName();
        String gameSummary = gameList.get(position).getSummary();
        holder.ivSummaryArrow.setVisibility(View.VISIBLE);
        if (gameSummary == null){
            gameSummary = "\n" + context.getString(R.string.summary_unavailable);
            holder.ivSummaryArrow.setVisibility(View.INVISIBLE);
        }

        String platformString = generatePlatformString(position);
        String genreString = generateGenreString(position);
        String ratingString = generateUserRatingString(position);
        String criticRatingString = generateCriticRatingString(position);
        String gameReleaseDateString = generateReleaseDateString(position);
        String imageUrl = generateCoverUrlString(position);
        String bgUrl = generateBgUrlString(position);
        Log.d(TAG, "onBindViewHolder: bgurl: " + bgUrl);
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivCover);

        Picasso.get()
                .load(bgUrl)
                .into(holder.ivBackground);



        // set TextViews
        holder.tvName.setText(gameName);
        holder.tvRating.setText(ratingString);
        holder.tvRatingCritic.setText(criticRatingString);
        holder.tvPlatform.setText(platformString);
        if (genreString == ""){
            holder.tvGenre.setVisibility(View.GONE);
        }else {
            holder.tvGenre.setText(genreString);
        }
        holder.tvSummary.setText(gameSummary);
        holder.tvReleaseDate.setText(gameReleaseDateString);
        holder.isThisAFavorite(gameList.get(position).getId());
        holder.ivCover.setContentDescription(gameName + " " + context.getString(R.string.content_description_iv_cover));

    }


    private String generateBgUrlString(int position) {
        // Image link format https://images.igdb.com/igdb/image/upload/t_{size}/{id}.jpg
        String bgId = "";
        List<Artwork> artworkList = gameList.get(position).getArtworks();
        List<Screenshot> screenshotList = gameList.get(position).getScreenshots();




        if (artworkList != null ){
            final int indexArt = new Random().nextInt(artworkList.size());
            bgId = artworkList.get(indexArt).getImageId();
        }else if (screenshotList != null){
            final int indexS = new Random().nextInt(screenshotList.size());
            bgId = screenshotList.get(indexS).getImageId();
        }
        return IgdbUtilities.IGDB_IMAGE_BASE_URL +
                IgdbUtilities.IGDB_IMAGE_SIZE_569x320 +
                bgId +
                IgdbUtilities.IGDB_IMAGE_FORMAT_JPG;
    }


    private String generateCoverUrlString(int position) {
        // Image link format https://images.igdb.com/igdb/image/upload/t_{size}/{id}.jpg
        String coverId = "";
        if (gameList.get(position).getCover() != null ){
            coverId = gameList.get(position).getCover().getImageId();
        }
        return IgdbUtilities.IGDB_IMAGE_BASE_URL +
                IgdbUtilities.IGDB_IMAGE_SIZE_264x374 +
                coverId +
                IgdbUtilities.IGDB_IMAGE_FORMAT_JPG;
    }

    private String generateReleaseDateString(int position) {
        int gameReleaseDateTimeStamp = gameList.get(position).getFirstReleaseDate();
        //Log.d(TAG, "onBindViewHolder: Release Timestamp: " + gameReleaseDateTimeStamp);
        String gameReleaseDateString = context.getString(R.string.to_be_determined_abbreviation);
        if (gameReleaseDateTimeStamp != 0){
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            gameReleaseDateString = sdf.format( (long) gameReleaseDateTimeStamp * 1000 );

        }
        return gameReleaseDateString;
    }

    private String generateUserRatingString (int position){
        double userScore = gameList.get(position).getRating();
        int userCount = gameList.get(position).getRatingCount();
        String ratingString = "";
        if (userCount == 0){
            ratingString = context.getString(R.string.to_be_determined_abbreviation);
        }else {
            ratingString = String.format(Locale.getDefault(), "%.1f", userScore / 10);
        }
        return ratingString;

    }

    private String generateCriticRatingString (int position){
        double criticScore = gameList.get(position).getAggregatedRating();
        int criticCount = gameList.get(position).getAggregatedRatingCount();
        String ratingString = "";
        if (criticCount == 0){
            ratingString = context.getString(R.string.to_be_determined_abbreviation);
        }else {
            ratingString = String.format(Locale.getDefault(), "%.1f", criticScore / 10);
        }
        return ratingString;

    }

    private String generateGenreString (int position){
        List<Genre> genreList = gameList.get(position).getGenres();
        String genreString = "";
        if (genreList != null){
            for ( int i = 0; i < genreList.size(); i++){
                genreString = genreString + genreList.get(i).getName();
                if ( i < genreList.size() - 1 ){
                    genreString = genreString + ", ";
                }
            }
        }

        Log.d(TAG, "generateGenreString: " + genreString);
        return genreString;
    }


    private String generatePlatformString(int position) {
        List<Platform> gamePlatforms = gameList.get(position).getPlatforms();
        String platformString = "";
        if (gamePlatforms != null){
            for ( int i = 0; i < gamePlatforms.size(); i++){
                if (gamePlatforms.get(i).getAbbreviation() == null ){
                    platformString = platformString + gamePlatforms.get(i).getName();

                }else {
                    platformString = platformString + gamePlatforms.get(i).getAbbreviation();
                }

                if ( i < gamePlatforms.size() - 1 ){
                    platformString = platformString + " | ";
                }
            }
        }
        //Log.d(TAG, "onBindViewHolder: PlatformString " + platformString);
        return platformString;
    }

    @Override
    public int getItemCount() {
        if (gameList == null){
            return 0;
        }else{
            return gameList.size();
        }
    }


    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        GameDatabase gameDatabase = GameDatabase.getInstance(context);
        OnGameListener onGameListener;
        //View vSeperator;
        TextView tvName, tvPlatform, tvGenre, tvSummary, tvRating, tvRatingCritic, tvReleaseDate;
                //, tvUserLabel, tvCriticLabel;
        ImageView ivCover, ivFavorite, ivCalendar, ivSummaryArrow, ivBackground;
        //, ivStarUser, ivStarCritic;
        boolean isFavorite;
        boolean isSummaryExpanded = false;

        public GameViewHolder(@NonNull View itemView, final OnGameListener onGameListener) {
            super(itemView);
            this.onGameListener = onGameListener;
            // create .xml for list item and bind the views here

            ivBackground = itemView.findViewById(R.id.iv_gameListItem_BackgroundArt);
            tvName = itemView.findViewById(R.id.tv_gameListItem_Name);
            tvPlatform = itemView.findViewById(R.id.tv_gameListItem_Platform);
            tvGenre = itemView.findViewById(R.id.tv_gameListItem_Genre);
            tvRating = itemView.findViewById(R.id.tv_gameListItem_Rating);
            tvSummary = itemView.findViewById(R.id.tv_gameListItem_Summary);
            ivCover = itemView.findViewById(R.id.iv_gameListItem_Cover);
            tvReleaseDate = itemView.findViewById(R.id.tv_gameListItem_ReleaseDate);
            ivFavorite = itemView.findViewById(R.id.iv_gameListItem_Favorite);
            ivCalendar = itemView.findViewById(R.id.iv_gameListItem_Calendar);
            tvRatingCritic = itemView.findViewById(R.id.tv_gameListItem_RatingCritic);
            ivSummaryArrow = itemView.findViewById(R.id.iv_dropArrow);

            /*
            vSeperator = itemView.findViewById(R.id.v_separator);
            ivStarUser = itemView.findViewById(R.id.iv_gameListItem_StarIcon);
            ivStarCritic = itemView.findViewById(R.id.iv_gameListItem_StarIconCritic);
            tvUserLabel = itemView.findViewById(R.id.tv_gameListItem_RatingLabel);
            tvCriticLabel = itemView.findViewById(R.id.tv_gameListItem_RatingCriticLabel);

             */


            itemView.setOnClickListener(this);

            tvSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSummaryExpanded){
                        isSummaryExpanded = false;
                        ivBackground.setVisibility(View.VISIBLE);
                        final float scale = context.getResources().getDisplayMetrics().density;
                        int pixels = (int) (60 * scale + 0.5f);
                        ViewGroup.LayoutParams layoutParams = tvSummary.getLayoutParams();
                        layoutParams.height = pixels;
                        tvSummary.setLayoutParams(layoutParams);
                        ivSummaryArrow.setRotation(0);

                    }else {
                        isSummaryExpanded = true;
                        ivBackground.setVisibility(View.INVISIBLE);
                        ViewGroup.LayoutParams layoutParams = tvSummary.getLayoutParams();
                        layoutParams.height = layoutParams.WRAP_CONTENT;
                        tvSummary.setLayoutParams(layoutParams);
                        ivSummaryArrow.setRotation(180);
                    }

                }
            });

            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Game clickedGame = gameList.get(getAdapterPosition());
                    displayFavoriteToast(isFavorite);

                    // Add To Favorites/ Wishlist
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d(TAG, "run: Is this a favorite? : " + isFavorite );
                            if (isFavorite){
                                //delete from db
                                isFavorite = false;
                                gameDatabase.gameDao().deleteGame(clickedGame);
                            } else {
                                //insert into db
                                isFavorite = true;
                                gameDatabase.gameDao().insertGame(clickedGame);
                            }

                        }
                    });

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CountdownWidget.class));
                    CountdownWidget.updateCountdownWidgets(context, appWidgetManager, appWidgetIds);


                }
            });

            ivCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Game clickedGame = gameList.get(getAdapterPosition());

                    String strTitle = clickedGame.getName() + " " + context.getString(R.string.release);
                    String strDescription = strTitle + "\n" +
                            generatePlatformString(getAdapterPosition());


                    int releaseDateTimeStamp = clickedGame.getFirstReleaseDate();
                    Date releaseDate = new Date( (long) releaseDateTimeStamp * 1000 );

                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.Events.TITLE, strTitle);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, releaseDate.getTime());
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, releaseDate.getTime());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, strDescription);
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {
            onGameListener.onGameClick(getAdapterPosition());

        }

        public void isThisAFavorite (final int gameId ){
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if ( gameDatabase.gameDao().loadGameById(gameId) == null) {
                        isFavorite = false;
                    } else{
                        isFavorite = true;
                    }

                }
            });

        }


        private void displayFavoriteToast (boolean isFavorite){
            if (!isFavorite) {
                Toast.makeText(context, context.getString(R.string.toast_favorite_add), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getString(R.string.toast_favorite_del), Toast.LENGTH_SHORT).show();
            }

        }

    }

    // interface for clicking the viewholder to open detail screen, Not yet needed
    public interface OnGameListener {
        // Send holder click to fragment
        void onGameClick (int position);
        // Send button clicks to Fragment
       // void onFavoritesButtonClick(View view, int position, boolean isFav);
        //void onCalendarButtonClick (View view, int position);
    }

}
