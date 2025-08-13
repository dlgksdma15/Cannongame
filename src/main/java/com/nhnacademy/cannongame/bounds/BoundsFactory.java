package com.nhnacademy.cannongame.bounds;

import com.nhnacademy.cannongame.Point;

public class BoundsFactory {
    public static Bounds createCircleBounds(Point center, double radius){
        if(radius <= 0) {
            throw new IllegalArgumentException("반지름은 0보다 커야 합니다.");
        }
        return new CircleBounds(center,radius);
    }

    public static Bounds createRectangleBounds(double x, double y, double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("너비와 높이는 0보다 커야 합니다.");
        }
        return new RectangleBounds(x, y, width, height);
    }
}
