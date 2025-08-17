package com.nhnacademy.cannongame.breakout;

import java.util.List;

// Exploding 인터페이스
public interface Exploding {
    void triggerExplosion(List<Breakable> bricks);
}

