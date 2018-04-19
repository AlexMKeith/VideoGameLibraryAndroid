package com.example.codygividen.videogamelibraryandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

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
        @Override
    public Context getContext(){
        return getApplicationContext();

    }

    @Override
    public void rowClicked(VideoGame videoGame) {
       if( videoGame.isCheckedOut()) {
           checkGameBackIn(videoGame);
       }else{
           checkGameOut(videoGame);
       }
    }

    @Override
    public void rowLongClicked(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Game?").setMessage("Are you sure you would like to delete this game?").setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                videoGameDatabase.videoGameDao().deleteVideoGame(videoGame);

                videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());

                Toast.makeText(MainActivity.this, "Game has been deleted!", Toast.LENGTH_LONG).show();
            }
        }).setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    private void checkGameOut(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("check-out Game?").setMessage("Are you sure you want to check this game out?")
                .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoGame.setCheckedOut(true);
                        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
                        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
                        Toast.makeText(MainActivity.this, "Game is checked out!", Toast.LENGTH_LONG).show();

                    }
                }).setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    private void checkGameBackIn(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check-in Game?").setMessage("Are you sure you would like to check in this game?")
                .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoGame.setCheckedOut(false);
                        //update database with updated game info
                        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
                        //let our adapter know the info in the database has changed to update our view accordingly
                        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
                        Toast.makeText(MainActivity.this, "Game is checked in!", Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}
