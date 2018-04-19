package com.example.codygividen.videogamelibraryandroid;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoGameAdapter extends RecyclerView.Adapter<VideoGameAdapter.ViewHolder> {

    private List<VideoGame> videoGameList;
    private AdapterCallback adapterCallback;
//    @BindView(R.id.item_row_layout)
//    protected Layout layoutcolor;

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
        holder.bind(videoGameList.get(position));

        holder.itemView.setOnClickListener(holder.onClick(videoGameList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(videoGameList.get(position)));
    }

    @Override
    public int getItemCount() {
        return videoGameList.size();
    }
    public void updateList(List<VideoGame> list){
        videoGameList = list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_row_layout)
        protected ConstraintLayout rowLayout;
        @BindView(R.id.item_title)
        protected TextView gameTitle;
        @BindView(R.id.item_genre)
        protected TextView gameGenre;
        @BindView(R.id.item_date)
        protected TextView gameDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(VideoGame videoGame) {
            gameTitle.setText(videoGame.getGameTitle());
            gameGenre.setText(adapterCallback.getContext().getString(R.string.game_genre, videoGame.getGameGenre()));

            if (videoGame.isCheckedOut()) {
                //make the due date visible
                gameDate.setVisibility(View.VISIBLE);
                //show the day the game was checked out on
                videoGame.setDate(new Date());
                //change background to red
                rowLayout.setBackgroundResource(R.color.red);
                //calculate check back in date
                int numberOfDays = 14;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(videoGame.getDate());
                calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                gameDate.setText(adapterCallback.getContext().getString(R.string.game_due_date, formatter.format(date)));
            } else {
                gameDate.setVisibility(View.INVISIBLE);
                rowLayout.setBackgroundResource(R.color.green);
            }
        }

        public View.OnClickListener onClick(final VideoGame videoGame){
            return new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(videoGame);
                }
            };
        }
            //Delete video game
        public View.OnLongClickListener onLongClick(final VideoGame videoGame) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(videoGame);
                    return true;
                }
            };
        }
    }
    public interface AdapterCallback{
        Context getContext();
        void rowClicked(VideoGame videoGame);
        void rowLongClicked(VideoGame videoGame);
    }

}