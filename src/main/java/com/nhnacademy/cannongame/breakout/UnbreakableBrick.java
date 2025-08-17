package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;

public class UnbreakableBrick extends StaticObject {
    public UnbreakableBrick(double x, double y, double width, double height) {
        super(x, y, width, height, BrickType.UNBREAKABLE.getColor(), CollisionAction.BOUNCE);
    }

    // UnbreakableBrick에 맞게 오버라이드된 handleCollision 메소드
    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Ball) {
            Ball ball = (Ball) other;

            // 공의 현재 위치와 벽의 위치를 비교하여 충돌 방향을 결정
            double ballCenterX = ball.getX() + ball.getRadius();
            double ballCenterY = ball.getY() + ball.getRadius();

            // 공이 벽의 위 또는 아래에 충돌했는지 확인
            boolean isVerticalCollision = ballCenterY < this.y || ballCenterY > this.y + this.height;

            if (isVerticalCollision) {
                // 수평 벽(상단/하단)과 충돌 시 Y 속도 반전
                ball.setDy(-ball.getDy());
            } else {
                // 수직 벽(좌측/우측)과 충돌 시 X 속도 반전
                ball.setDx(-ball.getDx());
            }
        }
    }
}
