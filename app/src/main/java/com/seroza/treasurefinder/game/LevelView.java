package com.seroza.treasurefinder.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import com.seroza.treasurefinder.R;

import java.util.List;

public class LevelView extends View {

    private final MediaPlayer mp;

    private Game game;

    private Paint circlePaint;

    private Context context;

    public LevelView(Context context, Game game) {
        super(context);
        this.context = context;
        this.game = game;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        mp = MediaPlayer.create(context, R.raw.loop);
        mp.setLooping(true);
        mp.start();
        mp.pause();
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
        if (game == null) return;
        for (Treasure t :
                game.getTreasures()) {
            canvas.drawCircle((float) t.getX(), (float) t.getY(), (float) t.getRadius(), circlePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        //mp.setVolume(0,0);

        switch (action & MotionEvent.ACTION_MASK) {

/*            case MotionEvent.ACTION_DOWN: {
                double x_cord = event.getX();
                double y_cord = event.getY();
                String text = "You click at x = " + event.getX() + " and y = " + event.getY();
                //Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                System.out.println(text);
                if (!game.findTreasure(x_cord, y_cord)) {
                    playSound(x_cord, y_cord);
                }
                else vibrate();
                break;
            }*/

            case MotionEvent.ACTION_MOVE: {
                double x_cord = event.getX();
                double y_cord = event.getY();
                if (!game.findTreasure(x_cord, y_cord)) {
                    playSound(x_cord, y_cord);
                }
                else {
                    vibrate();
                    invalidate();
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                mp.pause();
                break;
            }

        }
        return true;
    }

    private void playSound(double x_cord, double y_cord) {
        int soundScale = 100;
        double max = Math.hypot(getHeight(), getWidth()) * 0.4;
        double closest = game.getClosestDistance(x_cord, y_cord);
        int calc = (int) (soundScale - ((closest * soundScale) / max));
        if (calc > soundScale || calc <= 0){
            mp.pause();
            return;
        }
        float log1 = (float) (1 - Math.log(soundScale - calc) / Math.log(soundScale));
        mp.setVolume(log1, log1); //set volume takes two paramater
        if(!mp.isPlaying())
            mp.start();
    }

    private void vibrate(){
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

    private void pulse(){
    }
}
