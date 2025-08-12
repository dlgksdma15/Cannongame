package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import javafx.scene.paint.Color;

public class MovableBall extends PaintableBall{
    private Vector2D velocity;

    public MovableBall(Point center, double radius){
        super(center, radius);
    }

    public MovableBall(Point center, double radius, Color color) {
        super(center, radius, color);
        this.velocity = new Vector2D(0,0); // 속도를 0으로 초기화
    }

    public MovableBall(Point center, double radius, Color color, Vector2D velocity){
        super(center,radius,color);
        this.velocity = velocity;
    }

    // 시간 기반 이동
    public void move(double deltaTime) {
        Point currentCenter = getCenter(); // deltaTime이란? 이전 프레임과 현재 프레임 사이의 시간 간격
        Vector2D displacement = new Vector2D(velocity.getX() * deltaTime, velocity.getY() * deltaTime);
        Point newCenter = currentCenter.add(displacement);
        moveTo(newCenter);
    }

    public void move(){
        // 기본 60 FPS 가정
        // 메서드 활용
    }

    // 속도 set
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
}
