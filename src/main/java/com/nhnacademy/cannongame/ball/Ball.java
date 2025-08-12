package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;

public class Ball{
    private Point center; // Point 타입의 중심점
    private double radius; // 반지름

    // 생성자 - 위치 지정 필수
    public Ball(Point center, double radius) {
        // TODO: null 체크, 유효성 검사
        if(center == null){
            throw new IllegalArgumentException("Center는 null 값이 올 수 없습니다.");
        }
        if (radius <= 0) {
            throw new IllegalArgumentException("반지름은 0이 올 수 없습니다.");
        }
        this.center = center;
        this.radius = radius;

    }

    public Ball(double x, double y, double radius) {
        // TODO: Point 생성하여 다른 생성자 호출
        this(new Point(x, y), radius);
    }

    // Getter 메서드
    public Point getCenter() {
        // TODO: center 반환
        return center;
    }

    public double getRadius() {
        // TODO: radius 반환
        return radius;
    }

    // 위치 이동 메서드
    public void moveTo(Point newCenter) {
        // TODO: null 체크 후 center 업데이트
        if(newCenter == null){
            throw new IllegalArgumentException("New center에 null 값이 올 수 없습니다.");
        }
        this.center = newCenter;

    }

    // contains 메서드
    public boolean contains(Point p) {
        // TODO: center.distanceTo(p) 활용
        return this.center.distanceTo(p) <= this.radius;
        // this.center(5,5), p = (6,7) center랑 p 사이의 거리는 2.236이다.
        // 반지름 3이니까 반지름안에 저 좌표가 있냐고 묻는거 (공안에 p좌표가 포함되냐를 묻는거!)
    }

    // contains 메서드
    public boolean contains(double x, double y) {
        return contains(new Point(x, y));
    }

    public double getArea() {
        return radius * radius * Math.PI;
    }

    public boolean isColliding(Ball other){
        Point otherCenter = other.center;
        if(other == null){
            return false;
        }
        if(center.distanceTo(otherCenter) < radius + other.radius){
            return false;
        }
        return true;
    }
}