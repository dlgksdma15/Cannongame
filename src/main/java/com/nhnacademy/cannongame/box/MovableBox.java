package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;

public class MovableBox extends Box{
    private Vector2D velocity;

    public MovableBox(Point position, double width, double height) {
        super(position, width, height);
        this.velocity = new Vector2D(0,0);
    }

    public MovableBox(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Vector2D getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2D velocity){
        this.velocity = velocity;
    }

    public void move(double deltaTime) {
        // MovableBall과 동일한 로직: new_position = current_position + velocity * deltaTime
        Point newPosition = new Point(
                getPosition().getX() + velocity.getX() * deltaTime,
                getPosition().getY() + velocity.getY() * deltaTime
        );
        setPosition(newPosition);
    }
    // 테스트 코드에 필요한 getter 추가
    public double getDx() {
        return velocity.getX();
    }

    public double getDy() {
        return velocity.getY();
    }
}
