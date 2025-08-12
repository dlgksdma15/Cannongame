package com.nhnacademy.cannongame.world;

import com.nhnacademy.cannongame.BallCollision;
import com.nhnacademy.cannongame.CollisionDetector;
import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.BoundedBall;

import java.util.List;

public class BoundedWorld extends MovableWorld{

    public BoundedWorld(double width, double height) {
        super(width, height);
    }

    @Override
    public void add(Ball ball) {
        if(ball instanceof BoundedBall){
            ((BoundedBall) ball).setBounds(0,0,getWidth(),getHeight());
        }
        super.add(ball);
    }

    @Override
    public void update(double deltaTime){
        // 1. 모든 공 이동
        super.update(deltaTime);

        // 2. 벽과 공의 충돌 처리 (CollisionDetector 사용)
        for (Ball ball : getBalls()) {
            if (ball instanceof BoundedBall) {
                CollisionDetector.WallCollision collision = CollisionDetector.checkWallCollision(
                        (BoundedBall) ball, 0, 0, getWidth(), getHeight()
                );

                if (collision != null) {
                    CollisionDetector.resolveWallCollision((BoundedBall) ball, collision);
                }
            }
        }
        // 3. 공과 공의 충돌 처리
        List<Ball> balls = getBalls();
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);

                if (BallCollision.areColliding(ball1, ball2)) {
                    BallCollision.resolveElasticCollision(ball1, ball2);
                }
            }
        }

    }

//    private void handleWallCollision(MovableBall ball) {
//        Point center = ball.getCenter();
//        double radius = ball.getRadius();
//        Vector2D velocity = ball.getVelocity();
//
//        // 좌/우 경계
//        if(center.getX() - radius < 0){
//            ball.moveTo(new Point(radius, center.getY()));
//            ball.setVelocity(new Vector2D(-velocity.getX(), velocity.getY()));
//        } else if(center.getX() + radius > getWidth()){
//            ball.moveTo(new Point(getWidth() - radius, center.getY()));
//            ball.setVelocity(new Vector2D(-velocity.getX(), velocity.getY()));
//        }
//
//        // 상/하 경계
//        if(center.getY() - radius < 0){
//            ball.moveTo(new Point(center.getX(), radius));
//            ball.setVelocity(new Vector2D(velocity.getX(), -velocity.getY()));
//        } else if(center.getY() + radius > getHeight()){
//            ball.moveTo(new Point(center.getX(), getHeight() - radius));
//            ball.setVelocity(new Vector2D(velocity.getX(), -velocity.getY()));
//        }
//    }
}
