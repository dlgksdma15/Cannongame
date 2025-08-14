package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.CircleBounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Paintable, Movable, Collidable{
    // 위치와 크기
    private double x;
    private double y;
    private double radius;
    // 속도
    private double dx;
    private double dy;
    //색상
    private Color color;
    // 충돌 시 행동
    private CollisionAction collisionAction;
    private boolean isDestroyed = false;


    public Ball(double x, double y, double radius, Color color, CollisionAction collisionAction) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.collisionAction = collisionAction;
    }
    public Ball(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.collisionAction = CollisionAction.BOUNCE;

    }
    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = Color.RED;
        this.collisionAction = CollisionAction.BOUNCE;

    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    @Override
    public void move(double deltaTime){
        x += dx * deltaTime;
        y += dy * deltaTime;
    }
    // Collidable 구현
    @Override
    public Bounds getBounds(){
        return new CircleBounds(x,y,radius);
    }

    @Override
    public boolean isColliding(Boundable other){
        // 실제 충돌 검사 로직 (여기서는 사각형 경계 교차만 검사)
        return this.getBounds().intersects(other.getBounds()); // 겹치면 true 반환
    }
    @Override
    public void handleCollision(Collidable other) {
        switch (collisionAction) {
            case BOUNCE:
                // 충돌한 객체가 Movable이면 반사
                if (other instanceof Movable) {
                    Movable movableOther = (Movable) other;
                    movableOther.setDx(-movableOther.getDx());
                    movableOther.setDy(-movableOther.getDy());
                }
                // Ball 자신도 반사
                this.setDx(-this.getDx());
                this.setDy(-this.getDy());
                break;
            case DESTROY:
                // 제거 로직: 객체를 제거할 수 있는 방법
                this.isDestroyed = true;
                System.out.println("충돌로 인해 객체 파괴");
                break;
            case STOP:
                this.setDx(0);
                this.setDy(0);
                break;
            case PASS:
                // 아무것도 하지 않음 (통과)
                break;
            default:
                // 사용자 정의 충돌 처리
                System.out.println("사용자 정의 충돌 처리");
                break;

        };
    }
    @Override
    public CollisionAction getCollisionAction(){
        return collisionAction;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDx(double dx) {
        this.dx = dx;
    }

    @Override
    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCollisionAction(CollisionAction collisionAction) {
        this.collisionAction = collisionAction;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public boolean isDestroyed(){
        return isDestroyed;
    }
}
