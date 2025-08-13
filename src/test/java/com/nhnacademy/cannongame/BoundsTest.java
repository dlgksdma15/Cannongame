package com.nhnacademy.cannongame;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.CircleBounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundsTest {
    @Test
    public void testBoundsIntersection() {
        Bounds rect = new RectangleBounds(0, 0, 100, 100);
        Bounds circle = new CircleBounds(new Point(150, 50), 30);

        assertFalse(rect.intersects(circle)); // 직사각형 rect와 원 circle은 겹치지 않아야 한다
        // 겹쳤어? -> 아니(false)

        circle = new CircleBounds(new Point(80, 50), 30);
        assertTrue(rect.intersects(circle));
        // 겹쳤어? -> 응(true)
    }
}