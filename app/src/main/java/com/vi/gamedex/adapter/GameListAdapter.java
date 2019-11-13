package com.vi.gamedex.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vi.gamedex.R;
import com.vi.gamedex.model.Game;
import com.vi.gamedex.model.Platform;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {
    private static final String TAG = "GameListAdapter: ";
    private List<Game> gameList;
    private OnGameListener onGameListener;

    public GameListAdapter(OnGameListener listener) {
        this.onGameListener = listener;
    }

    public GameListAdapter(OnGameListener listener, List<Game> gList) {
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
        //create .xml for list item and inflate here
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
        OnGameListener onGameListener;
        TextView tvName, tvPlatform, tvSummary, tvRating, tvReleaseDate;
        ImageView ivCover;

        public GameViewHolder(@NonNull View itemView, OnGameListener onGameListener) {
            super(itemView);
            this.onGameListener = onGameListener;
            // create .xml for list item and bind the views here

            tvName = itemView.findViewById(R.id.tv_gameListItem_Name);
            tvPlatform = itemView.findViewById(R.id.tv_gameListItem_Platform);
            tvRating = itemView.findViewById(R.id.tv_gameListItem_Rating);
            tvSummary = itemView.findViewById(R.id.tv_gameListItem_Summary);
            ivCover = itemView.findViewById(R.id.iv_gameListItem_Cover);
            tvReleaseDate = itemView.findViewById(R.id.tv_gameListItem_ReleaseDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onGameListener.onGameClick(getAdapterPosition());
        }
    }

    public interface OnGameListener {
        void onGameClick (int position);
    }

}
