package com.nhnacademy.cannongame.simpleworld;

import javafx.scene.paint.Color;

public class MovableBox extends Box implements Movable{
    private double dx;
    private double dy;

    // 생성자: Box의 생성자를 호출하고, 추가로 속도(dx, dy)를 초기화합니다
    public MovableBox(double x, double y, double width, double height, Color color, CollisionAction collisionAction,double dx, double dy){
        super(x,y,width,height,color,collisionAction);
        this.dx = dx;
        this.dy = dy;
    }
    public MovableBox(double x, double y, double width, double height, Color color){
        super(x,y,width,height,color);
    }


    public MovableBox(double x, double y, double width, double height){
        super(x,y,width,height);
    }

    @Override
    public void move(double deltaTime) {
        this.x += this.dx * deltaTime;
        this.y += this.dy * deltaTime;
    }

    @Override
    public double getDx() {
        return this.dx;
    }

    @Override
    public double getDy() {
        return this.dy;
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
