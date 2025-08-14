package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import javafx.scene.paint.Color;

public class MovableBall extends PaintableBall{
    private Vector2D velocity;

    // 생성자 1: 색상과 속도를 기본값으로 초기화
    public MovableBall(Point center, double radius) {
        this(center, radius, Color.RED, new Vector2D(0, 0));
    }

    // 생성자 2: 색상만 지정하고 속도는 기본값으로 초기화
    public MovableBall(Point center, double radius, Color color) {
        this(center, radius, color, new Vector2D(0, 0));
    }

    // 생성자 3: 모든 필드를 받아 초기화 (최종 생성자)
    public MovableBall(Point center, double radius, Color color, Vector2D velocity) {
        super(center, radius, color); // 부모 클래스(PaintableBall) 생성자 호출
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
