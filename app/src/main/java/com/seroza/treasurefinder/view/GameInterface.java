package com.seroza.treasurefinder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.seroza.treasurefinder.R;
import com.seroza.treasurefinder.game.Bomb;
import com.seroza.treasurefinder.game.Game;
import com.seroza.treasurefinder.game.Treasure;

import java.util.Random;

public class GameInterface extends View {
    private int[] sounds = {R.raw.loop1, R.raw.loop2, R.raw.loop3, R.raw.loop4, R.raw.loop5, R.raw.loop6, R.raw.loop7, R.raw.loop8, R.raw.loop9, R.raw.loop10, R.raw.loop11};

    boolean showObjects = false;
    private int currentLevel;

    private MediaPlayer mpTreasure;
    private MediaPlayer mpBomb;
    private TTS tts;
    private Game game;

    private Paint pTreasure;
    private Paint pBomb;

    private Context context;

    public GameInterface(Context context, Game game, TTS tts) {
        super(context);
        this.context = context;
        this.game = game;
        this.tts = tts;
        setBackground(ContextCompat.getDrawable(context, R.drawable.img_background));
        generatePaints();
        generateBombSound();
        generateTreasureSound();
        currentLevel = game.getLevel();
        tts.speak("Level " + game.getLevel());
    }

    private void generatePaints() {
        pTreasure = new Paint();
        pTreasure.setStyle(Paint.Style.FILL);
        pBomb = new Paint();
        pBomb.setStyle(Paint.Style.FILL);
        pBomb.setColor(Color.RED);
    }

    private void generateBombSound() {
        mpBomb = MediaPlayer.create(context, R.raw.bomb);
        mpBomb.setLooping(true);
        mpBomb.start();
        mpBomb.pause();
    }

    private void generateTreasureSound() {
        Random random = new Random();
        mpTreasure = MediaPlayer.create(context, sounds[random.nextInt(sounds.length)]);
        mpTreasure.setLooping(true);
        mpTreasure.start();
        mpTreasure.pause();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        game.setWidth(getWidth());
        game.setHeight(getHeight());
        game.start(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (game == null || !showObjects) return;

        for (Treasure t :
                game.getTreasures()) {
            canvas.drawCircle((float) t.getX(), (float) t.getY(), (float) t.getRadius(), pTreasure);
        }
        for (Bomb b :
                game.getBombs()) {
            canvas.drawCircle((float) b.getX(), (float) b.getY(), (float) b.getRadius(), pBomb);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        //mp.setVolume(0,0);

        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_POINTER_DOWN: {
                if (event.getPointerCount() == 3) {
                    showObjects = !showObjects;
                    invalidate();
                }
                Log.i("LevelView", "onTouchEvent: " + event.getPointerCount());
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                double x_cord = event.getX();
                double y_cord = event.getY();
                if (!game.findTreasure(x_cord, y_cord)) {
                    playTreasureSound(x_cord, y_cord);
                    playBombSound(x_cord, y_cord);
                } else {
                    tts.speak("Treasure found");
                    vibrate();
                    invalidate();
                    if (game.getLevel() != currentLevel) {
                        nextLevel();
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                pauseSound();
                break;
            }

        }
        return true;
    }

    private void nextLevel() {
        mpBomb.release();
        mpTreasure.release();
        generateTreasureSound();
        generateBombSound();
        tts.speak("Level " + game.getLevel());
        currentLevel = game.getLevel();
    }

    private void pauseSound() {
        mpTreasure.pause();
        mpBomb.pause();
    }

    private void playTreasureSound(double x_cord, double y_cord) {
        int soundScale = 100;
        double max = Math.hypot(getHeight(), getWidth()) * 0.4;
        double closest = game.getClosestTreasureDistance(x_cord, y_cord);
        int calc = (int) (soundScale - ((closest * soundScale) / max));
        if (calc > soundScale || calc <= 0) {
            mpTreasure.pause();
            return;
        }
        float log1 = (float) (1 - Math.log(soundScale - calc) / Math.log(soundScale));
        mpTreasure.setVolume(log1, log1); //set volume takes two paramater
        if (!mpTreasure.isPlaying())
            mpTreasure.start();
    }

    private void playBombSound(double x_cord, double y_cord) {
        int soundScale = 100;
        double max = Math.hypot(getHeight(), getWidth()) * 0.25;
        double closest = game.getClosestBombDistance(x_cord, y_cord);
        int calc = (int) (soundScale - ((closest * soundScale) / max));
        if (calc > soundScale || calc <= 0) {
            mpBomb.pause();
            return;
        }
        float log1 = (float) (1 - Math.log(soundScale - calc) / Math.log(soundScale));
        mpBomb.setVolume(log1, log1); //set volume takes two paramater
        if (!mpBomb.isPlaying())
            mpBomb.start();
    }

    private void vibrate() {
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        int strong_vibration = 5; //vibrate with a full power for 30 secs
        int interval = 1000;
        int dot = 1; //one millisecond of vibration
        int short_gap = 10; //one millisecond of break - could be more to weaken the vibration
        long[] pattern = {
                0, 100, 1000, 300
        };
        // Vibrate for 400 milliseconds
        if (v != null) {
            v.vibrate(pattern, -1);
        }
    }

    public interface TTS {
        void speak(String text);
    }

}
