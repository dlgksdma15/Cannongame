package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.Vector2D;

public interface PhysicsObject {
    Vector2D getPosition();
    Vector2D getVelocity();
    void setVelocity(Vector2D velocity);
    double getMass();
    void applyForce(Vector2D force);
    void update(double deltaTime);
}