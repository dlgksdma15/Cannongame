package com.nhnacademy.cannongame.app;

import com.nhnacademy.cannongame.world.BoundedWorld;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdvancedCollisionApp extends MovableWorldApp{
    private BoundedWorld world;
    private Label fpsLevel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        // AdvancedCollisionApp, Canvas, GraphicsContext 생성
        world = new BoundedWorld(WORLD_WIDTH,WORLD_HEIGHT);
        canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // 다양한 크기의 공 생성
        createBalls();

    }

    private void createBalls() {

    }
}
