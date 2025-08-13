package com.nhnacademy.cannongame.entity;

import com.nhnacademy.cannongame.Bounds;
import com.nhnacademy.cannongame.Point;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected Point point;
    protected Bounds bounds;

    public abstract void update(double deltaTime);
    public abstract void render(GraphicsContext gc);
    public abstract void handleCollision(GameObject object); // 충돌 처리

}
