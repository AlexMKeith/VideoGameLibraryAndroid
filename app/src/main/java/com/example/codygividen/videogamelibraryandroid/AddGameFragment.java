package com.example.codygividen.videogamelibraryandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGameFragment extends Fragment {
    private ActivityCallback activityCallback;
    private VideoGameDatabase videoGameDatabase;


    @BindView(R.id.add_game_title_edit_text)
    protected EditText gameTitle;
    @BindView(R.id.add_game_genre_edit_text)
    protected EditText gameGenre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public static AddGameFragment newInstance() {

        Bundle args = new Bundle();
        AddGameFragment fragment = new AddGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        videoGameDatabase = ((VideoGameApplication) getActivity().getApplicationContext()).getDatabase();
    }

    @OnClick(R.id.add_game_fab)
    protected void addButtonClicked(){

        if(gameTitle.getText().toString().isEmpty() || gameGenre.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Try Again, please fill in all options!!",Toast.LENGTH_LONG).show();
        }else{
            String title = gameTitle.getText().toString();
            String genre = gameGenre.getText().toString();
            addGameToDatabase(title, genre);
            Toast.makeText(getActivity(), "Game Added!!!",Toast.LENGTH_LONG).show();

        }
    }


    private void addGameToDatabase(final String title, final String genre){
        VideoGame videoGame = new VideoGame(title, genre, new Date());
        videoGameDatabase.videoGameDao().addVideoGame(videoGame);
        activityCallback.addClicked();

    }

    public void attachParent(ActivityCallback activityCallback){
        this.activityCallback = activityCallback;

    }
    public interface ActivityCallback{
        void addClicked();

    }

}
