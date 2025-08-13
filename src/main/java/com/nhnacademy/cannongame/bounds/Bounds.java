package com.nhnacademy.cannongame.bounds;

import com.nhnacademy.cannongame.Point;

public abstract class Bounds {

    public abstract double getMinX();
    public abstract double getMinY();
    public abstract double getMaxX();
    public abstract double getMaxY();

    public double getWidth(){
        return getMaxX() - getMinX();
    }
    public double getHeight(){
        return getMaxY() - getMinY();
    }
    public double getCenterX(){
        return getMinX() + getWidth() / 2.0; // (최소 x + 너비 ) / 2 = 가운데 x
    }
    public double getCenterY(){
        return getMinY() + getHeight() / 2.0;
    }
    public double getArea(){
        if(this instanceof CircleBounds) {
            CircleBounds circle = (CircleBounds) this;
            return Math.PI * Math.pow(circle.getRadius(), 2);
        } else if(this instanceof RectangleBounds){
            return getWidth() * getHeight();
        }
        return 0;
    }
    // 점 포함 여부
    public boolean contains(Point point){
        return (point.getX() >= getMinY() && point.getX() <= getMaxX()) &&
                (point.getY() >= getMinY() && point.getY() <= getMaxY());
    }
    // 다른 Bounds 포함 여부
    public boolean contains(Bounds other){
        return getMinX() <= other.getMinX() &&
                getMaxX() >= other.getMaxX() &&
                getMinY() <= other.getMinY() &&
                getMaxX() >= other.getMaxY();
    }
    public boolean contains(double x, double y) {
        return (x >= getMinX() && x <= getMaxX()) &&
                (y >= getMinY() && y <= getMaxY());
    }
    // 교차 여부
    public boolean intersects(Bounds other){
        return !(other.getMaxX() < getMinX() ||
                other.getMinX() > getMaxX() ||
                other.getMaxY() < getMinY() ||
                other.getMinY() > getMaxY());
    }

}
