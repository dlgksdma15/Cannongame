package com.nhnacademy.cannongame;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class MovableWorld extends World{
    private final ArrayList<Ball> balls = new ArrayList<>();

    public MovableWorld(double width, double height){
        super(width, height);
    }

    @Override
    public void add(Ball ball) {
        super.add(ball);
    }

    @Override
    public void remove(Ball ball) {
        super.remove(ball);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public List<Ball> getBalls() {
        return super.getBalls();
    }

    @Override
    public double getWidth() {
        return super.getWidth();
    }

    @Override
    public double getHeight() {
        return super.getHeight();
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
    }

    @Override
    public double getBallCount() {
        return super.getBallCount();
    }

    public void update(double deltaTime){
        // 모든 공들의 위치 업데이트
        for (Ball ball : getBalls()) {
            // 공이 MovableVall 타입인지 확인
            if(ball instanceof MovableBall){
                MovableBall movable = (MovableBall) ball;
                // 1. 중력 적용: 속도 변경
                //Vector2D newVelocity = movableBall.getVelocity().add(gravity.multiply(deltaTime));
                // 2. 속도에 따라 공 위치 적용
                movable.move(deltaTime);
            }
        }
    }
}
