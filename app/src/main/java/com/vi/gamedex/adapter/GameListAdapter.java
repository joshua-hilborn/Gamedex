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
import com.vi.gamedex.model.Video;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {
    private static final String TAG = "GameListAdapter: ";
    private List<Game> gameList;
    private OnGameListener onGameListener;

    public GameListAdapter(List<Game> gList, OnGameListener listener) {
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
        Log.d(TAG, "onBindViewHolder: Name: " + gameName);
        String gameSummary = gameList.get(position).getSummary();
        String youtubeBaseUrl = "https://www.youtube.com/watch?v=";
        List<Video> trailers = gameList.get(position).getVideos();
        if (trailers != null){
            for ( Video video : trailers ){
                Log.d(TAG, "onBindViewHolder: Videos: " + video.getName() + " " + youtubeBaseUrl + video.getVideoId() );
            }
        }

        //https://images.igdb.com/igdb/image/upload/t_{size}/{hash}.jpg
        String gameCoverBaseUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/";
        String coverId = "";
        if (gameList.get(position).getCover() != null ){
            coverId = gameList.get(position).getCover().getImageId();
            //Log.d(TAG, "onBindViewHolder: Cover: " + gameList.get(position).getCover().getImageId());
            //Log.d(TAG, "onBindViewHolder: url: " + gameList.get(position).getCover().getUrl());
        }
        String imageUrl = gameCoverBaseUrl + coverId + ".jpg";
        Log.d(TAG, "onBindViewHolder: Cover: " + imageUrl);


        Picasso.get()
                .load(imageUrl)
                //.load("https:" + gameCoverUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivCover);

        // set name and summary Textview
        holder.tvName.setText(gameName);
        holder.tvSummary.setText(gameSummary);

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
        TextView tvName, tvSummary;
        ImageView ivCover;

        public GameViewHolder(@NonNull View itemView, OnGameListener onGameListener) {
            super(itemView);
            this.onGameListener = onGameListener;
            // create .xml for list item and bind the views here

            tvName = itemView.findViewById(R.id.tv_gameListItem_Name);
            tvSummary = itemView.findViewById(R.id.tv_gameListItem_Summary);
            ivCover = itemView.findViewById(R.id.iv_gameListItem_Cover);

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
