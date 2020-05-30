package com.seroza.treasurefinder.game;

public class Treasure {
    private double x;
    private double y;
    private double radius;
    private double velocityX;
    private double velocityY;

    public Treasure(double x, double y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double distanceFrom(double x, double y){
        return Math.hypot(this.x - x, this.y - y);
    }

    public boolean isColliding(double x, double y){
        return distanceFrom(x , y) <= radius;
    }

    public boolean find(double x, double y){
        if(isColliding(x, y))
            return true;
        return false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
