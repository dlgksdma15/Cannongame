package com.nhnacademy.cannongame;

public class CircleBounds extends Bounds{
    private Point point;
    private double centerX;
    private double centerY;
    private double radius;


    public CircleBounds(double centerX, double centerY, double radius) {
        if(radius < 0){
            throw new IllegalArgumentException("반지름은 0보다 작을 수 없습니다.");
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }
    public CircleBounds(Point point, double radius){
        if(radius < 0){
            throw new IllegalArgumentException("반지름은 0보다 작을 수 없습니다.");
        }
        this.point = point;
        this.centerX = point.getX();
        this.centerY = point.getY();
        this.radius = radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double getMinX() {
        return centerX - radius;
    }

    @Override
    public double getMinY() {
        return centerY - radius;
    }

    @Override
    public double getMaxX() {
        return centerX + radius;
    }

    @Override
    public double getMaxY() {
        return centerY + radius;
    }
}
