package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.SwipeEvent;
import javafx.scene.paint.Color;

// 경계나 장애물로 사용되는 Box 클래스
public class Box implements Paintable, Collidable, Movable{
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double dx;
    protected double dy;
    protected Color color;
    protected CollisionAction collisionAction;

    public Box(double x, double y, double width, double height, Color color, CollisionAction collisionAction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.collisionAction = collisionAction;
    }
    public Box(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.collisionAction = CollisionAction.BOUNCE;
    }

    public Box(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.RED;
        this.collisionAction = CollisionAction.BOUNCE;
    }

    @Override
    public void paint(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRect(x, y, width, height); // 사각형 내부를 채웁니다.
        gc.setStroke(Color.BLACK); // 테두리 색상을 검정색으로 설정합니다.
        gc.setLineWidth(1); // 테두리 두께를 설정합니다.
        gc.strokeRect(x, y, width, height); // 사각형 테두리를 그립니다.
    }
    @Override
    public Bounds getBounds(){
        return new RectangleBounds(x,y,width,height);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public void handleCollision(Collidable other) {
        switch (collisionAction) {
            case BOUNCE:
                // 충돌한 객체를 반사시킵니다.
                if (other instanceof Movable) {
                    Movable movableOther = (Movable) other;
                    movableOther.setDx(-movableOther.getDx());
                    movableOther.setDy(-movableOther.getDy());
                }

                // Box 자신도 Movable이므로, 반사 로직을 추가
//                this.setDx(-this.getDx());
//                this.setDy(-this.getDy());

                break;
            case DESTROY:
                // 충돌한 객체를 파괴합니다. (여기서는 메시지 출력)
                System.out.println("충돌한 객체를 파괴합니다.");
                break;
            case STOP:
                // 충돌한 객체의 움직임을 멈춥니다.
                if (other instanceof Movable) {
                    Movable movableOther = (Movable) other;
                    movableOther.setDx(0);
                    movableOther.setDy(0);
                }
                break;
            case PASS:
                // 아무것도 하지 않습니다.
                break;
            default:
                // 사용자 정의 액션
                break;
        }
    }

    @Override
    public CollisionAction getCollisionAction() {
        return collisionAction;
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
    @Override
    public void move(double deltaTime) {
        this.x += this.dx * deltaTime;
        this.y += this.dy * deltaTime;
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
}
