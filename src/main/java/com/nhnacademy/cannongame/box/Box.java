package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.bounds.RectangleBounds;

public class Box {
    private Point position;
    private double width; // 사각형 너비 (오른쪽 방향)
    private double height; // 사각형의 높이 (아래쪽 방향)

    public Box(Point position, double width, double height) {
        if(position == null){
            throw new IllegalArgumentException("position은 null 값이 올 수 없습니다.");
        }
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("너비와 높이는 0보다 커야 합니다.");
        }
        this.position = position;
        this.width = width;
        this.height = height;
    }
    public Box(double x, double y, double width, double height){
        this(new Point(x,y), width, height);
    }
    public void move(double deltaTime){
    }

    public void setPosition(Point position) {
        if (position == null) {
            throw new IllegalArgumentException("Position은 null일 수 없습니다.");
        }
        this.position = position;
    }

    // 점이 박스 안에 있는지 확인하는 contains 메서드
    // JavaFX 좌표계는 Y축이 아래로 증가하므로, Y 좌표 비교 시 이 점을 고려해야 합니다.
    public boolean contains(double px, double py) {
        return px >= position.getX() && px <= (position.getX() + width) &&
                py >= position.getY() && py <= (position.getY() + height);
    }
    // 새로운 contains 메서드 (Point 객체 사용)
    public boolean contains(Point p) {
        if(p == null){
            throw new IllegalArgumentException("Point p는 null일 수 없습니다.");
        }
        return contains(p.getX(), p.getY());
    }

    // Box의 경계를 나타내는 RectangleBounds 객체를 반환
    public RectangleBounds getBounds() {
        return new RectangleBounds(position.getX(), position.getY(), width, height);
    }

    public Point getPosition() {
        return position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public double getX(){
        return position.getX();
    }
    public double getY(){
        return position.getY();
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setHeight(double height){
        this.height = height;
    }
    public void moveTo(Point newPosition) {
        if (newPosition == null) {
            throw new IllegalArgumentException("New position은 null일 수 없습니다.");
        }
        this.position = newPosition;
    }

}
