package com.seroza.treasurefinder.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public final static int WIDTH_PADDING = 100;
    public final static int HEIGHT_PADDING = 100;

    private int width;
    private int height;
    private int level;

    private List<Treasure> treasures = new ArrayList<>();

    public boolean findTreasure(double x, double y) {
        for (Treasure t : treasures) {
            if (t.find(x, y)) {
                // TODO: 5/26/2020 uncomment this
                treasures.remove(t);
                return true;
            }
        }
        return false;
    }

    public Game() {
    }

    public double getClosestDistance(double x, double y) {
        double min = height;
        for (Treasure t : treasures) {
            double dist = t.distanceFrom(x, y);
            if (dist < min)
                min = dist;
        }
        return min;
    }


    public void newTreasure(int level) {
        Random random = new Random();
        double x = WIDTH_PADDING + ((width - WIDTH_PADDING) - WIDTH_PADDING) * random.nextDouble();
        double y = HEIGHT_PADDING + ((height - HEIGHT_PADDING) - HEIGHT_PADDING) * random.nextDouble();
        treasures.add(new Treasure(x, y, level));
    }

    public void newTreasure(int level, double velocityX, double velocityY) {
        Random random = new Random();
        double x = WIDTH_PADDING + ((width - WIDTH_PADDING) - WIDTH_PADDING) * random.nextDouble();
        double y = HEIGHT_PADDING + ((height - HEIGHT_PADDING) - HEIGHT_PADDING) * random.nextDouble();
        treasures.add(new Treasure(Math.abs(x), Math.abs(y), level));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public void start(int level) {
        newTreasure(level);
        newTreasure(level);
    }
}
