package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.CircleBounds;
import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.entity.Vector2D;

public abstract class AbstractBall {
    protected Point center;
    protected double radius;
    protected Bounds bounds;

    // 속도 관련 필드 추가
    protected double dx = 0; // x축 방항 속도
    protected double dy = 0; // y축 방향 속도

    public AbstractBall(Point center, double radius){
        if(center == null || radius <= 0){
            throw new IllegalArgumentException("Center는 null일 수 없으며, 반지름은 0보다 작을 수 없습니다.");
        }
        this.center = center;
        this.radius = radius;
        this.bounds = new CircleBounds(center.getX(), center.getY(), radius);
    }

    // 템플릿 메서드 : 업데이트의 전체적인 흐름을 정의
    // 훅 메서드
    public final void update(double deltaTime){
        beforeUpdate(deltaTime); // 전처리 (기본 구현 비어있음)
        performUpdate(deltaTime); // 핵심 로직 (추상 메서드)
        afterUpdate(deltaTime); // 후처리 (기본 구현 비어있음)
        updateBounds(deltaTime); // 경계 업데이트
    }
    // 업데이트 전 전처리 작업수행
    protected void beforeUpdate(double deltaTime) {

    }

    protected abstract void performUpdate(double deltaTime);
    protected void afterUpdate(double deltaTime){

    }
    protected void updateBounds(double deltaTime){
        this.bounds = new CircleBounds(center.getX(), center.getY(), radius);
    }

    public Point getCenter() {
        return center;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public double getRadius(){
        return radius;
    }
    public void setVelocity(Vector2D velocity) {
        this.dx = velocity.getX();
        this.dy = velocity.getY();
    }

    public Vector2D getVelocity(){
        return new Vector2D(dx,dy);
    }
    // getter, setter
    public double getX() {
        return center.getX();
    }

    public double getY() {
        return center.getY();
    }

    public void setX(double x) {
        this.center = new Point(x, getY());
    }

    public void setY(double y) {
        this.center = new Point(getX(), y);
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

}
