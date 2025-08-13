package com.nhnacademy.cannongame.app;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.BoundedBall;
import com.nhnacademy.cannongame.ball.MovableBall;
import com.nhnacademy.cannongame.ball.PaintableBall;
import com.nhnacademy.cannongame.world.BoundedWorld;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

/**
 * 고급 충돌 시뮬레이션을 구현하는 애플리케이션입니다.
 * BoundedWorld를 사용하여 여러 공의 움직임과 충돌을 처리합니다.
 */
public class AdvancedCollisionApp extends MovableWorldApp {
    private BoundedWorld world;
    private Canvas canvas;
    private GraphicsContext gc;
    private CheckBox gravityCheckBox;
    private Slider ballCountSlider;
    private Label fpsLabel;
    private long lastTime = 0;

    private static final double GRAVITY = 500; // 중력 가속도
    private static final int INITIAL_BALL_COUNT = 10; // 초기 공의 개수

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        world = new BoundedWorld(WORLD_WIDTH, WORLD_HEIGHT);
        canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        HBox controls = new HBox();
        gravityCheckBox = new CheckBox("중력 적용");
        ballCountSlider = new Slider(1, 100, INITIAL_BALL_COUNT);
        fpsLabel = new Label("FPS: 0");
        controls.getChildren().addAll(gravityCheckBox, new Label("공 개수:"), ballCountSlider, fpsLabel);
        root.setTop(controls);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("고급 충돌 시뮬레이션");
        stage.show();

        // 슬라이더 값이 변경되면 공을 다시 생성
        ballCountSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            world.clear();
            createBalls(newValue.intValue());
        });

        createBalls(INITIAL_BALL_COUNT);

        // 게임 루프 타이머
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1e9;
                lastTime = now;

                if (gravityCheckBox.isSelected()) {
                    applyGravity(deltaTime);
                }

                world.update(deltaTime);
                render();

                double fps = 1.0 / deltaTime;
                fpsLabel.setText(String.format("FPS: %.2f", fps));
            }
        };
        timer.start();
    }

    /**
     * 지정된 개수만큼 공을 생성하여 월드에 추가합니다.
     * 각 공은 랜덤한 크기와 속도를 가집니다.
     * @param count 생성할 공의 개수
     */
    private void createBalls(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double radius = 10 + random.nextDouble() * 20; // 10 ~ 30 크기의 공
            double x = radius + random.nextDouble() * (WORLD_WIDTH - 2 * radius);
            double y = radius + random.nextDouble() * (WORLD_HEIGHT - 2 * radius);
            Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            BoundedBall ball = new BoundedBall(new Point(x, y), radius, color);

            // 공에 랜덤한 초기 속도 부여
            double vx = (random.nextDouble() * 200 - 100); // -100 ~ 100
            double vy = (random.nextDouble() * 200 - 100); // -100 ~ 100
            ball.setVelocity(new Vector2D(vx, vy));

            world.add(ball);
        }
    }

    /**
     * 모든 움직이는 공에 중력을 적용합니다.
     * @param deltaTime 지난 시간
     */
    private void applyGravity(double deltaTime) {
        for (Ball ball : world.getBalls()) {
            if (ball instanceof MovableBall) {
                MovableBall movable = (MovableBall) ball;
                // 현재 속도에 중력 가속도를 더함
                movable.getVelocity().setY(movable.getVelocity().getY() + GRAVITY * deltaTime);
            }
        }
    }

    /**
     * 캔버스에 모든 공을 그립니다.
     */
    private void render() {
        gc.clearRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT); // 캔버스 초기화
        for (Ball ball : world.getBalls()) {
            if (ball instanceof PaintableBall) {
                PaintableBall paintable = (PaintableBall) ball;
                gc.setFill(paintable.getColor());
                gc.fillOval(paintable.getCenter().getX() - paintable.getRadius(),
                        paintable.getCenter().getY() - paintable.getRadius(),
                        paintable.getRadius() * 2, paintable.getRadius() * 2);
            }
        }
    }
}
