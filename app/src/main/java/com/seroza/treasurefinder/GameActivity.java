package com.seroza.treasurefinder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.seroza.treasurefinder.game.Game;
import com.seroza.treasurefinder.view.LevelView;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private LevelView levelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game();
        levelView = new LevelView(this, game);
        //setContentView(R.layout.activity_game);
        setContentView(levelView);
    }
}
