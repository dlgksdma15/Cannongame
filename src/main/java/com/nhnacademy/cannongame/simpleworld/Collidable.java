package com.nhnacademy.cannongame.simpleworld;

public interface Collidable extends Boundable{
    public void handleCollision(Collidable other); // 충돌 처리
    public CollisionAction getCollisionAction(); // 충돌 액션 반환
}
