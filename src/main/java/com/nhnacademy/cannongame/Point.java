package com.nhnacademy.cannongame;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point other) {
        // x 좌표 간의 차이 (x2 - x1)
        double deltaX = other.getX() - this.x;

        // y 좌표 간의 차이 (y2 - y1)
        double deltaY = other.getY() - this.y;

        // 피타고라스 정리 적용: sqrt((deltaX)² + (deltaY)²)
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Point add(Vector2D vector){
        return new Point(x + vector.getX(), y + vector.getY());
    }

    public Vector2D subtract(Point other){
        return new Vector2D(x - other.x, y - other.y);
    }
}