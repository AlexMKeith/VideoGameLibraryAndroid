package com.example.codygividen.videogamelibraryandroid;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoGameAdapter extends RecyclerView.Adapter<VideoGameAdapter.ViewHolder> {

    private List<VideoGame> videoGameList;
    private AdapterCallback adapterCallback;
    @BindView(R.id.item_row_layout)
    protected Layout layoutcolor;

    public VideoGameAdapter(List<VideoGame> videoGameList, AdapterCallback adapterCallback) {
        this.videoGameList = videoGameList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_game, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);

        holder.itemView.setOnClickListener(holder.onClick(videoGameList.get(position));)
        holder.itemView.setOnLongClickListener();
    }

    @Override
    public int getItemCount() {
        return videoGameList.size();
    }
    public void updateList(List<VideoGame> list){
        videoGameList = list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_row_layout)
        protected RelativeLayout rowLayout;
        @BindView(R.id.item_title)
        protected TextView gameTitle;
        @BindView(R.id.item_genre)
        protected TextView gameGenre;
        @BindView(R.id.item_date)
        private TextView gameDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        public void bind(VideoGame videoGame) {
            VideoGameApplication videoGameApplication = new VideoGameApplication();
            Resources res = videoGameApplication
            gameTitle.setText(videoGame.getGameTitle());
            gameGenre.setText(getReasources().getString(R.string.game_genre, videoGame.));
            gameDate.setText(videoGameList.get().getDate().toString());
        }

        @Override
        public void onClick(View v) {
            //To Check out game
            videoGameList.get(getAdapterPosition()).setGameTitle("CheckedOutGame");
            notifyDataSetChanged();
            layoutcolor.
        }
        //To delete game
        @Override
        public boolean onLongClick(View v) {

            return true;
        }




    }
    public interface AdapterCallback{

    }
}