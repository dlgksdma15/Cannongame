package com.nhnacademy.cannongame.breakout;


public interface Force {
    /**
     * 주어진 물리 객체에 힘을 적용합니다.
     * @param obj 힘을 적용받을 PhysicsObject 객체
     * @param deltaTime 이전 프레임으로부터의 시간(초)
     */
    void apply(PhysicsObject obj, double deltaTime);
}