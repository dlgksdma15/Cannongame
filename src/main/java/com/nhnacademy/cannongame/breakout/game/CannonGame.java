package com.nhnacademy.cannongame.breakout.game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CannonGame extends Application {
    // 필드
    private CannonGameWorld world;
    private Stage primaryStage;
    private Timeline gameLoop;
    private Canvas canvas;
    private GraphicsContext gc;

    // UI 요소
    private Label scoreLabel;
    private Label shotsLabel;

    private static final double WORLD_WIDTH = 800;
    private static final double WORLD_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showMainMenu();
    }

    private void showMainMenu() {
        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: #f4f4f4;");

        Label titleLabel = new Label("Cannon Game");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        Button startButton = new Button("게임 시작");
        startButton.setOnAction(e -> startGame());

        Button exitButton = new Button("종료");
        exitButton.setOnAction(e -> Platform.exit());

        menuLayout.getChildren().addAll(titleLabel, startButton, exitButton);
        Scene scene = new Scene(menuLayout, WORLD_WIDTH, WORLD_HEIGHT);

        primaryStage.setTitle("Cannon Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame() {
        // 게임 모드 설정 (예시로 CLASSIC 사용)
        world = new CannonGameWorld(WORLD_WIDTH, WORLD_HEIGHT, GameMode.CLASSIC);
        world.loadLevel(1); // 첫 번째 레벨 로드
        world.createCannon(100, WORLD_HEIGHT - 50); // 대포 생성

        BorderPane root = new BorderPane();
        canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root.setCenter(canvas);
        root.setBottom(createControlPanel());

        Scene gameScene = new Scene(root, WORLD_WIDTH, WORLD_HEIGHT);

        setupInputHandlers(gameScene);
        createGameLoop();
        gameLoop.play();

        primaryStage.setScene(gameScene);
    }

    private void setupInputHandlers(Scene scene) {
        scene.setOnMousePressed(e -> {
            if (world.getCannon() != null) {
                world.getCannon().startCharging();
            }
        });
        scene.setOnMouseReleased(e -> {
            if (world.getCannon() != null) {
                world.fire();
            }
        });
        scene.setOnMouseMoved(e -> {
            if (world.getCannon() != null) {
                // 마우스 위치에 따라 대포 각도 조절
                double dx = e.getX() - world.getCannon().getX();
                double dy = e.getY() - world.getCannon().getY();
                double angle = Math.atan2(dy, dx);
                world.getCannon().setAngle(angle);
            }
        });
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    }

    private HBox createControlPanel() {
        HBox controlPanel = new HBox(20);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #333;");

        scoreLabel = new Label("점수: 0");
        scoreLabel.setTextFill(Color.WHITE);
        shotsLabel = new Label("남은 포탄: 무제한");
        shotsLabel.setTextFill(Color.WHITE);

        controlPanel.getChildren().addAll(scoreLabel, shotsLabel);
        return controlPanel;
    }

    private void handleKeyPress(KeyCode code) {
        if (world.getCannon() != null) {
            switch (code) {
                case UP:
                    world.getCannon().adjustAngle(Math.toRadians(1));
                    break;
                case DOWN:
                    world.getCannon().adjustAngle(Math.toRadians(-1));
                    break;
                case SPACE:
                    world.fire();
                    break;
            }
        }
    }

    private void createGameLoop() {
        final long[] lastFrameTime = {0};
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            long now = System.nanoTime();
            double deltaTime = (now - lastFrameTime[0]) / 1_000_000_000.0;
            if (lastFrameTime[0] == 0) {
                deltaTime = 0;
            }
            lastFrameTime[0] = now;

            // UI 업데이트
            scoreLabel.setText("점수: " + world.getScore());
            shotsLabel.setText("남은 포탄: " + (world.getShotsRemaining() == Integer.MAX_VALUE ? "무제한" : world.getShotsRemaining()));

            world.update(deltaTime);
            gc.clearRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            world.render(gc);
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }
}