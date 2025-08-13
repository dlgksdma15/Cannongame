package com.nhnacademy.cannongame;

import com.nhnacademy.cannongame.ball.SimpleMovableBall;
import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.CircleBounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import com.nhnacademy.cannongame.entity.Vector;
import com.nhnacademy.cannongame.entity.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractTypeTest {

    @Test
    public void testVector2DOperations() {
        Vector v1 = new com.nhnacademy.cannongame.entity.Vector2D(3,4);
        assertEquals(5.0,v1.magnitude(),0.001);

        Vector v2 = v1.normalize();
        assertEquals(1.0, v2.magnitude(), 0.001);
        assertEquals(0.6, v2.get(0), 0.001);
        assertEquals(0.8, v2.get(1), 0.001);
    }

    @Test
    public void testBoundsContainment() {
        Bounds outer = new RectangleBounds(0, 0, 200, 200);
        Bounds inner = new CircleBounds(100, 100, 50);

        assertTrue(outer.contains(inner));
        assertFalse(inner.contains(outer));

        assertTrue(outer.contains(100, 100));
        assertTrue(inner.contains(100, 100));
    }

    @Test
    public void testAbstractBallUpdate() {
        SimpleMovableBall ball = new SimpleMovableBall(new Point(100, 100), 20);
        ball.setVelocity(new Vector2D(50, 30));

        Point oldCenter = ball.getCenter(); //
        double oldX = oldCenter.getX();
        double oldY = oldCenter.getY();

        ball.update(1.0);

        Point newCenter = ball.getCenter();
        assertEquals(oldX + 50, newCenter.getX(), 0.001);
        assertEquals(oldY + 30, newCenter.getY(), 0.001);

        // Bounds도 업데이트되었는지 확인
        assertEquals(newCenter.getX(), ball.getBounds().getCenterX(), 0.001);
        assertEquals(newCenter.getY(), ball.getBounds().getCenterY(), 0.001);
    }
}