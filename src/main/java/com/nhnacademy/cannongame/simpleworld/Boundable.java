package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;

public interface Boundable {
    Bounds getBounds(); // 객체 반환
    boolean isColliding(Boundable other); // 충돌 여부 확인
}
