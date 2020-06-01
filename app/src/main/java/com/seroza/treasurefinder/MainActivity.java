package com.seroza.treasurefinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    boolean inButton = false;
    boolean inButton1 = false;
    boolean inButton2 = false;
    private Button btnStart;
    private Button btnContinue;
    private List<Button> myButtons = new ArrayList<>();
    private TextToSpeech tts;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        btnStart = findViewById(R.id.btnStart);
        btnContinue = findViewById(R.id.btnContinue);
        myButtons.add(btnStart);
        myButtons.add(btnContinue);
        setButtonEnterListener();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
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

    private void setButtonEnterListener() {
        getWindow().getDecorView()
                .findViewById(android.R.id.content)
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View arg0, MotionEvent event) {
                        int action = event.getAction();


                        Rect hitRect = new Rect();
                        Button button;
                        Log.d("LOG", "onTouch: " + inButton);
                        for (int i = 0; i < myButtons.size(); i++) {
                            button = myButtons.get(i);
                            button.getHitRect(hitRect);
                            if (i == 0)
                                if (hitRect.contains((int) event.getX(), (int) event.getY())) {
                                    if (!inButton1) {
                                        vibrate();
                                        speak(myButtons.get(i).getText().toString());
                                        inButton1 = true;
                                    }
                                    if (action == MotionEvent.ACTION_UP) {
                                        newGame();
                                    }
                                    return true;
                                } else {
                                    inButton1 = false;
                                }
                            else if (hitRect.contains((int) event.getX(), (int) event.getY())) {
                                if (!inButton2) {
                                    vibrate();
                                    speak(myButtons.get(i).getText().toString());
                                    inButton2 = true;
                                }
                                if (action == MotionEvent.ACTION_UP) {
                                    continueGame();
                                }
                                return true;
                            } else {
                                inButton2 = false;
                            }
                        }
                        return true;
                    }

                });
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void newGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("continue", false);
        startActivity(intent);
    }

    private void continueGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("continue", true);
        startActivity(intent);
    }


    public void speak(String text) {
        message = text;
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
    }


    private void vibrate() {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        int strong_vibration = 5; //vibrate with a full power for 30 secs
        int interval = 1000;
        int dot = 1; //one millisecond of vibration
        int short_gap = 10; //one millisecond of break - could be more to weaken the vibration
        long[] pattern = {
                0, 100
        };
        // Vibrate for 400 milliseconds
        if (v != null) {
            v.vibrate(pattern, -1);
        }
    }
}
