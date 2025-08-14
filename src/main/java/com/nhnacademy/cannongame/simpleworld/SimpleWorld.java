package com.nhnacademy.cannongame.simpleworld;

import com.nhnacademy.cannongame.bounds.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SimpleWorld {
    private double width;
    private double height;
    private List<Object> gameObjects; // Object 리스트 (다양한 타입 저장)
    private List<Box> boundaries; // 경계 Box들
    private double objectCount;


    public SimpleWorld(double width, double height) {
        this.width = width;
        this.height = height;
        this.gameObjects = new ArrayList<>();
        this.boundaries = new ArrayList<>();
        this.objectCount = 0;
    }

    public SimpleWorld(double width, double height, List<Object> gameObjects, List<Box> boundaries) {
        this.width = width;
        this.height = height;
        this.gameObjects = gameObjects;
        this.boundaries = boundaries;
    }
    // 객체 추가 메서드
    public void add(Object obj) {
        gameObjects.add(obj);
        objectCount++;
    }
    public void addObject(Object obj) {
        gameObjects.add(obj);
        objectCount++;
    }

    public void createBoundaries(){
        // 상단 경계 (화면 위)
        boundaries.add(new Box(0, -10, width, 10, Color.GRAY, CollisionAction.BOUNCE));
        // 하단 경계 (화면 아래)
        boundaries.add(new Box(0, height, width, 10, Color.GRAY, CollisionAction.BOUNCE));
        // 좌측 경계 (화면 왼쪽)
        boundaries.add(new Box(-10, 0, 10, height, Color.GRAY, CollisionAction.BOUNCE));
        // 우측 경계 (화면 오른쪽)
        boundaries.add(new Box(width, 0, 10, height, Color.GRAY, CollisionAction.BOUNCE));

    }
    public void update(double deltaTime){
        // 1. Movable 객체들 이동
        for(Object obj : gameObjects){
            if(obj instanceof Movable){
                ((Movable) obj).move(deltaTime);
            }
        }
        // 2. 경계와의 충돌 처리
        for (Object obj : gameObjects) {
            if (obj instanceof Collidable) {
                Collidable collidableObj = (Collidable) obj;
                for (Box boundary : boundaries) {
                    if (collidableObj.isColliding(boundary)) {
                        collidableObj.handleCollision(boundary);
                    }
                }
            }
        }
        // 3. 객체 간 충돌 처리 (추가된 로직)
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                Object obj1 = gameObjects.get(i);
                Object obj2 = gameObjects.get(j);

                // 둘 다 Collidable 객체인지 확인
                if (obj1 instanceof Collidable && obj2 instanceof Collidable) {
                    Collidable collidable1 = (Collidable) obj1;
                    Collidable collidable2 = (Collidable) obj2;

                    if (collidable1.isColliding(collidable2)) {
                        // 충돌 처리 로직을 한 번만 호출
                        collidable1.handleCollision(collidable2);
                    }
                }
            }
        }
    }
    public double getObjectCount(){
        return objectCount;
    }
    public void render(GraphicsContext gc){

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<Box> getBoundaries() {
        return boundaries;
    }

    public List<Object> getGameObjects() {
        return gameObjects;
    }
}
