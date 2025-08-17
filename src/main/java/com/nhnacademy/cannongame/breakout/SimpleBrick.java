package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;

public class SimpleBrick extends StaticObject implements Breakable {
    private boolean isDestroyed = false;
    private int points;


    public SimpleBrick(double x, double y, double width, double height, BrickType type) {
        // BrickType에서 색상과 CollisionAction을 가져와 super() 호출
        super(x, y, width, height, type.getColor(), CollisionAction.BOUNCE);
        this.points = type.getPoints(); // BrickType에서 점수를 가져와 초기화
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Ball) {
            this.hit(); // 공과 충돌 시 hit() 호출

            // 공의 y축 속도를 반전시킵니다.
            Ball ball = (Ball) other;
            ball.setDy(-ball.getDy());
        }
    }

    @Override
    public void hit() {
        this.isDestroyed = true;
        // 점수 추가 등 추가 로직을 여기에 구현할 수 있습니다.
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }
    public int getPoints(){
        return points;
    }

}