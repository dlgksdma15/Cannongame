package com.nhnacademy.cannongame;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovableBallTest {
    @Test
    public void testMovableBallMovement(){
        Point initialCenter = new Point(100,100);
        MovableBall ball = new MovableBall(initialCenter,20, Color.RED);
        ball.setVelocity(new Vector2D(50,30));

        ball.move(1.0); // 1초 동안 이동

        Point newCenter = ball.getCenter();
        assertEquals(150, newCenter.getX(),0.001);
        assertEquals(130,newCenter.getY(),0.001);

    }

    @Test
    public void testVelocityMagnitude(){
        MovableBall ball = new MovableBall(new Point(0,0),10,Color.BLUE);
        Vector2D vector2D = new Vector2D(3,4);

    }
}