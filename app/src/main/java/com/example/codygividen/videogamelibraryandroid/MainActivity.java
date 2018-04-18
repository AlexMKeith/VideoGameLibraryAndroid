package com.example.codygividen.videogamelibraryandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements VideoGameAdapter.AdapterCallback, AddGameFragment.ActivityCallback {

    @BindView(R.id.games_recycler_view)
    protected RecyclerView recyclerView;

    private VideoGameDatabase videoGameDatabase;
    private VideoGameAdapter videoGameAdapter;
    private List<VideoGame> videoGameList;
    private AddGameFragment addGameFragment;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.games_recycler_view);
        videoGameDatabase = ((VideoGameApplication) getApplicationContext()).getDatabase();

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        videoGameAdapter = new VideoGameAdapter(videoGameDatabase.videoGameDao().getVideoGames(),this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoGameAdapter);
        videoGameAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.add_game_button)
    protected void addGameButtonClicked(){
        addGameFragment = AddGameFragment.newInstance();
        addGameFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addGameFragment).commit();
    }


    @Override
    public void addClicked() {
        getSupportFragmentManager().beginTransaction().remove(addGameFragment).commit();
        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
        }

    }
}
