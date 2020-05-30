package com.seroza.treasurefinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.seroza.treasurefinder.game.Game;
import com.seroza.treasurefinder.view.GameInterface;

import java.util.Locale;

public class GameActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, GameInterface.TTS {

    private Game game;
    private GameInterface gameInterface;
    private TextToSpeech tts;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        savedInstanceState.getInt("");
        game = new Game(loadGame());
        gameInterface = new GameInterface(this, game, this);
        //setContentView(R.layout.activity_game);
        setContentView(gameInterface);
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            // tts.setPitch(0.5f); // set pitch level

            tts.setSpeechRate(0.6f); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speak(message);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void speak(String text) {
        message = text;
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
    }

    private void saveGame() {
        SharedPreferences sp = getSharedPreferences("game_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("level", game.getLevel());
        editor.apply();
    }

    private int loadGame() {
        SharedPreferences sp = getSharedPreferences("game_prefs", Activity.MODE_PRIVATE);
        return sp.getInt("level", 1);
    }

    private void newGame() {
        SharedPreferences sp = getSharedPreferences("game_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("level", 1);
        editor.apply();
    }
}
