package com.seroza.treasurefinder.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public final static int WIDTH_PADDING = 100;
    public final static int HEIGHT_PADDING = 100;

    private int width;
    private int height;
    private int level = 1;

    private List<Treasure> treasures = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();

    public Game(int level) {
        this.level = level;
    }

    public boolean findTreasure(double x, double y) {
        for (Treasure t : treasures) {
            if (t.find(x, y)) {
                treasures.remove(t);
                if (treasures.isEmpty())
                    loadLevel(++level);
                return true;
            }
        }
        return false;
    }

    public double getClosestTreasureDistance(double x, double y) {
        double min = height;
        for (Treasure t : treasures) {
            double dist = t.distanceFrom(x, y);
            if (dist < min)
                min = dist;
        }
        return min;
    }

    public double getClosestBombDistance(double x, double y) {
        double min = height;
        for (Bomb b : bombs) {
            double dist = b.distanceFrom(x, y);
            if (dist < min)
                min = dist;
        }
        return min;
    }

    public void newTreasure(int radius) {
        Random random = new Random();
        double x = WIDTH_PADDING + ((width - WIDTH_PADDING) - WIDTH_PADDING) * random.nextDouble();
        double y = HEIGHT_PADDING + ((height - HEIGHT_PADDING) - HEIGHT_PADDING) * random.nextDouble();
        treasures.add(new Treasure(x, y, radius));
    }

    public void newBomb(int level) {
        Random random = new Random();
        double x = WIDTH_PADDING + ((width - WIDTH_PADDING) - WIDTH_PADDING) * random.nextDouble();
        double y = HEIGHT_PADDING + ((height - HEIGHT_PADDING) - HEIGHT_PADDING) * random.nextDouble();
        bombs.add(new Bomb(x, y, level));
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

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public void start(int level) {
        loadLevel(1);
    }

    private void loadLevel(int level) {
        treasures.clear();
        bombs.clear();
        switch (level) {
            case 1:
                treasures.add(new Treasure(width * 0.5, height * 0.4, 100));
                break;
            case 2:
                treasures.add(new Treasure(width * 0.9, height * 0.9, 100));
                break;
            case 3:
                treasures.add(new Treasure(width * 0.2, height * 0.2, 100));
                treasures.add(new Treasure(width * 0.5, height * 0.8, 100));
                break;
            case 4:
                bombs.add(new Bomb(width * 0.5, height * 0.8, 50));
                treasures.add(new Treasure(width * 0.5, height * 0.2, 100));
                break;
            default:
                newTreasure(50);
        }
    }

    public void save() {

    }

    public void load() {

    }
}
