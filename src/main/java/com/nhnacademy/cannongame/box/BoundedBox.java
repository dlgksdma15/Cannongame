package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;

public class BoundedBox extends Box {
    public BoundedBox(Point position, double width, double height) {
        super(position, width, height);
    }

    public BoundedBox(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}