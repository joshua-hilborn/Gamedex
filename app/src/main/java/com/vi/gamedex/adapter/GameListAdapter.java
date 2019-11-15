package com.vi.gamedex.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vi.gamedex.AppExecutors;
import com.vi.gamedex.R;
import com.vi.gamedex.database.GameDatabase;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.model.Platform;
import com.vi.gamedex.viewmodel.GameListViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        Log.d(TAG, "onBindViewHolder: PlatformString " + platformString);
        String gameSummary = gameList.get(position).getSummary();
        if (gameSummary == null){
            gameSummary = holder.itemView.getContext().getString(R.string.summary_unavailable);
        }
        double criticScore = gameList.get(position).getAggregatedRating();
        int criticCount = gameList.get(position).getAggregatedRatingCount();
        double userScore = gameList.get(position).getRating();
        int userCount = gameList.get(position).getRatingCount();
        String ratingString = "";

        if (criticCount >= userCount){
            if (criticCount == 0){
                ratingString = holder.itemView.getContext().getString(R.string.to_be_determined_abbreviation);
            }else {
                ratingString = String.format(Locale.getDefault(), "%.1f", criticScore / 10);
            }
        } else {
            ratingString = String.format(Locale.getDefault(), "%.1f", userScore / 10);

        }

        Log.d(TAG, "onBindViewHolder: Name: " + gameName + "(" + gameList.get(position).getId() + ")");
        Log.d(TAG, "onBindViewHolder: Ratings: " + "Critic: " + criticScore + " from " + criticCount + " User: " + userScore + " from " + userCount );

        //https://images.igdb.com/igdb/image/upload/t_{size}/{hash}.jpg
        String gameCoverBaseUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/";
        String coverId = "";
        if (gameList.get(position).getCover() != null ){
            coverId = gameList.get(position).getCover().getImageId();
        }
        String imageUrl = gameCoverBaseUrl + coverId + ".jpg";
        Log.d(TAG, "onBindViewHolder: Cover: " + imageUrl);


        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivCover);

        int gameReleaseDateTimeStamp = gameList.get(position).getFirstReleaseDate();
        Log.d(TAG, "onBindViewHolder: Release Timestamp: " + gameReleaseDateTimeStamp);
        String gameReleaseDateString = holder.itemView.getContext().getString(R.string.to_be_determined_abbreviation);
        if (gameReleaseDateTimeStamp != 0){
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            gameReleaseDateString = sdf.format( (long) gameReleaseDateTimeStamp * 1000 );

        }
        // set TextViews
        holder.tvName.setText(gameName);
        holder.tvRating.setText(ratingString);
        holder.tvPlatform.setText(platformString);
        holder.tvSummary.setText(gameSummary.substring(0, Math.min(gameSummary.length(), 160)));
        holder.tvReleaseDate.setText(gameReleaseDateString);
        holder.isThisAFavorite(gameList.get(position).getId());
        //holder.setFavoriteDrawable(holder.isFavorite);

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
        //GameListViewModel gameListViewModel;
        //GameListRepository repo = GameListRepository.getInstance(context.);
        OnGameListener onGameListener;
        TextView tvName, tvPlatform, tvSummary, tvRating, tvReleaseDate;
        ImageView ivCover, ivFavorite, ivCalendar;
        boolean isFavorite;

        public GameViewHolder(@NonNull View itemView, final OnGameListener onGameListener) {
            super(itemView);
            this.onGameListener = onGameListener;
            // create .xml for list item and bind the views here

            tvName = itemView.findViewById(R.id.tv_gameListItem_Name);
            tvPlatform = itemView.findViewById(R.id.tv_gameListItem_Platform);
            tvRating = itemView.findViewById(R.id.tv_gameListItem_Rating);
            tvSummary = itemView.findViewById(R.id.tv_gameListItem_Summary);
            ivCover = itemView.findViewById(R.id.iv_gameListItem_Cover);
            tvReleaseDate = itemView.findViewById(R.id.tv_gameListItem_ReleaseDate);
            ivFavorite = itemView.findViewById(R.id.iv_gameListItem_Favorite);
            ivCalendar = itemView.findViewById(R.id.iv_gameListItem_Calendar);
            //gameListViewModel = ViewModelProviders.of((FragmentActivity) context).get(GameListViewModel.class);

            setFavoriteDrawable(isFavorite);




            itemView.setOnClickListener(this);

            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //onGameListener.onFavoritesButtonClick(v, getAdapterPosition(), isFavorite);
                    final Game clickedGame = gameList.get(getAdapterPosition());

                    setFavoriteDrawable(!isFavorite);

                    // Add To Favorites/ Wishlist
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d(TAG, "run: Is this a favorite? : " + gameListViewModel.isThisAFavorite(clickedGame.getId()) );
                            Log.d(TAG, "run: Is this a favorite? : " + isFavorite );
                            if (isFavorite){
                                //delete from db
                                isFavorite = false;
                                gameDatabase.gameDao().deleteGame(clickedGame);
                                //gameListViewModel.deleteFavorite(clickedGame);
                            } else {
                                //insert into db
                                isFavorite = true;
                                gameDatabase.gameDao().insertGame(clickedGame);
                                //gameListViewModel.addFavorite(clickedGame);
                            }

                        }
                    });

                }
            });

            ivCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onGameListener.onCalendarButtonClick(v, getAdapterPosition());

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
                    //if ( gameListViewModel.isThisAFavorite(gameId)) {
                    if ( gameDatabase.gameDao().loadGameById(gameId) == null) {
                        isFavorite = false;
                        setFavoriteDrawable(isFavorite);
                    } else{
                        isFavorite = true;
                        setFavoriteDrawable(isFavorite);
                    }

                }
            });


        }

        private void setFavoriteDrawable (boolean isFavorite){
            if (isFavorite) {
                //isFavorite = false;
                ivFavorite.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                //mFavoritesButtonImageView.setImageResource(R.drawable.ic_favorite_filled_black_24);
            } else {
               // isFavorite = false;
                ivFavorite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }



    }

    // interface for clicking the viewholder to open detail screen
    public interface OnGameListener {
        // Send holder click to fragment
        void onGameClick (int position);
        // Send button clicks to Fragment
       // void onFavoritesButtonClick(View view, int position, boolean isFav);
        //void onCalendarButtonClick (View view, int position);
    }



}
