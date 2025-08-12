package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import javafx.scene.paint.Color;

public class BoundedBall extends MovableBall{
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public BoundedBall(Point center, double radius){
        super(center,radius);
    }
    // 생성자에서 경계 초기화 (경계 없음 상태)
    public BoundedBall(Point center, double radius, Color color) {
        super(center, radius, color);
        // 초기값: 경계가 설정되지 않은 상태를 나타냄
        this.minX = Double.MIN_VALUE;
        this.minY = Double.MIN_VALUE;
        this.maxX = Double.MAX_VALUE;
        this.maxY = Double.MAX_VALUE;
    }
    // 경계 설정 시 공의 중심이 이동 가능한 범위
    public void setBounds(double minX, double minY, double maxX, double maxY){
        this.minX = minX + getRadius();
        this.maxX = maxX - getRadius();

        this.minY = minY + getRadius();
        this.maxY = maxY - getRadius();
    }
    // move 메서드에서 경계 충돌 처리
    @Override
    public void move(double deltaTime){
        // 다음 위치 계산
        Point nextPoint = getCenter().add(getVelocity().multiply(deltaTime));
        double restitution = 0.8; // 반발 계수 (탄성)

        // 경계가 설정된 경우에만 충돌 검사
        // Double.MIN_VALUE와 Double.MAX_VALUE는 경계가 없음을 의미
        if(minX > Double.MIN_VALUE && maxX < Double.MAX_VALUE){
            if(nextPoint.getX() <= minX || nextPoint.getX() >= maxX){
                // X축 속도 방향을 반대로 바꾸고 반발 계수 적용
                setVelocity(new Vector2D(-getVelocity().getX() * restitution, getVelocity().getY()));

                // 위치를 경계선에 정확히 맞춤
                if(nextPoint.getX() <= this.minX){
                    nextPoint = new Point(this.minX, nextPoint.getY());
                }else{
                    nextPoint = new Point(this.maxX, nextPoint.getY());
                }

            }
        }

        if (minY > Double.MIN_VALUE && maxY < Double.MAX_VALUE) {
            if (nextPoint.getY() <= minY || nextPoint.getY() >= maxY) {
                // Y축 속도 방향을 반대로 바꾸고 반발 계수 적용
                setVelocity(new Vector2D(getVelocity().getX(), -getVelocity().getY() * restitution));
                // 위치를 경계선에 정확히 맞춤
                if (nextPoint.getY() <= this.minY) {
                    nextPoint = new Point(nextPoint.getX(), this.minY);
                } else {
                    nextPoint = new Point(nextPoint.getX(), this.maxY);
                }

            }
        }

        // 부모 클래스의 move 호출
        moveTo(nextPoint);
    }
}
