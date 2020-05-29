package com.seroza.treasurefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.seroza.treasurefinder.game.Game;
import com.seroza.treasurefinder.game.LevelView;

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
