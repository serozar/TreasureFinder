package com.seroza.treasurefinder.game;

public class Bomb extends Treasure {
    public Bomb(double x, double y, int level) {
        super(x, y, level);
        setRadius(50);
    }
}
