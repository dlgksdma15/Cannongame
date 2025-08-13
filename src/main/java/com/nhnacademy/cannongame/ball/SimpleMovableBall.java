package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.entity.Vector;
import com.nhnacademy.cannongame.entity.Vector2D;

public class SimpleMovableBall extends AbstractBall{
    private Vector velocity;

    public SimpleMovableBall(Point center, double radius) {
        super(center, radius);
    }


    @Override
    protected void performUpdate(double deltaTime) {
        // 속도와 시간에 따라 위치를 업데이트하는 핵심 로직 구현
        double newX = center.getX() + velocity.get(0) * deltaTime;
        double newY = center.getY() + velocity.get(1) * deltaTime;
        this.center = new Point(newX,newY);
    }

    public void setVelocity(Vector2D velocity){
        this.velocity = velocity;
    }

    public Vector getVelocity(){
        return velocity;
    }
}
