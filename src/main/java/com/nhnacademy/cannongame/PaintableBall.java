package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PaintableBall extends Ball {
    private Color color;

    public PaintableBall(Point center, double radius, Color color) {
        super(center, radius);
        if(color == null){
            throw new IllegalArgumentException("색상은 null이 올 수 없습니다.");
        }
        this.color = color;
    }

    public PaintableBall(Point center, double radius){
        super(center, radius);
        this.color = Color.RED;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color){
        if(color == null){
            color = Color.RED;
            //throw new IllegalArgumentException("색상은 null일 수 없습니다.");

        }
        this.color = color;
    }

    public void draw(GraphicsContext gc){
        Point center = getCenter();
        double leftX = center.getX() - getRadius(); // center의 x위치에서 반지름을 빼면 왼쪽 X좌표 나옴
        double topY = center.getY() - getRadius(); // center의 y위치에서 반지름을 뺴면 위쪽 Y좌표 나옴
        double diameter = getRadius() * 2; // 지름은 반지름 * 2

        gc.setFill(this.color);
        gc.fillOval(leftX, topY, diameter, diameter); // (leftX, topY, 타원의 너비, 타원의 높이)

    }
}
