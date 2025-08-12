package com.nhnacademy.cannongame.app;

import com.nhnacademy.cannongame.ball.MovableBall;
import com.nhnacademy.cannongame.world.MovableWorld;
import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MovableWorldApp extends BallWorldApp {
//    private static final int WORLD_WIDTH = 800;
//    private static final int WORLD_HEIGHT = 600;
//    private final Random random = new Random();
//    private Canvas canvas;
//    private GraphicsContext gc;
    private MovableWorld world;

    private Label fpsLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // MovableWorld, Canvas, GraphicsContext 생성
        world = new MovableWorld(WORLD_WIDTH, WORLD_HEIGHT);
        canvas = new Canvas(WORLD_WIDTH,WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // FPS 표시 레이블 설정
        fpsLabel = new Label("FPS: 0");
        fpsLabel.setTextFill(Color.BLUE);

        // 레이아웃 설정, Scene 구성 및 Stage에 설정
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, WORLD_WIDTH, WORLD_HEIGHT);
        stage.setTitle("Movable World with AnimationTimer");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();

        // 렌덤 속도를 가진 공 10개 생성
        createMovingBall(10);

        // 게임 루프 시작
        GameLoop timer = new GameLoop();
        timer.start();


    }

    private void createMovingBall(int count) {
        for(int i = 0; i < count; i++){
            double radius = random.nextDouble() * 20 + 10; // 10~30 픽셀
            double x = random.nextDouble() * (WORLD_WIDTH - (2 * radius)) + radius;
            double y = random.nextDouble() * (WORLD_HEIGHT - (2 * radius)) + radius;
            Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            // 속도 x,y를 -100 ~ 100 사이의 값으로 설정
            double velocityX = random.nextDouble() * 200 - 100;
            double velocityY = random.nextDouble() * 200 - 100;
            Vector2D velocity = new Vector2D(velocityX,velocityY);

            MovableBall movableBall = new MovableBall(new Point(x, y), radius, color, velocity);

            world.add(movableBall);
        }
    }
    private class GameLoop extends AnimationTimer{
        private long lastFrameTime = System.nanoTime();
        private long lastFpsTime = 0;
        private int frameCount = 0;

        @Override
        public void handle(long now) {
            // 1. 델타 타임 계산
            double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = now;

            // 2. 월드 상태 업데이트
            world.update(deltaTime);

            // 3. 월드 그리기
            gc.clearRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT); // 이전 프레임 지우기
            world.draw(gc);

            // 4. FPS 계산 및 표시
            updateFPS();

        }

        private void updateFPS() {
            frameCount++;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFpsTime >= 1000) { // 1초마다 갱신
                int fps = frameCount;
                frameCount = 0;
                lastFpsTime = currentTime;
                // UI 스레드에서 레이블 업데이트
                Platform.runLater(() -> fpsLabel.setText("FPS: " + fps));
            }
        }
    }
}

