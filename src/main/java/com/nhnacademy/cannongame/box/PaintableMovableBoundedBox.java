package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import javafx.scene.paint.Color;

public class PaintableMovableBoundedBox extends MovableBoundedBox {
    private Color color;

    public PaintableMovableBoundedBox(Point position, double width, double height, Color color) {
        super(position, width, height);
        if (color == null) {
            throw new IllegalArgumentException("색상은 null이 올 수 없습니다.");
        }
        this.color = color;
    }

    public PaintableMovableBoundedBox(double x, double y, double width, double height, Color color) {
        this(new Point(x, y), width, height, color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == null) {
            color = Color.RED;
        }
        this.color = color;
    }
}