package com.nhnacademy.cannongame.breakout.game;

import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.breakout.BreakoutWorldApp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CannonGameApp extends BreakoutWorldApp {
    // 필드 설계
    private CannonGameWorld world;
    private Stage primaryStage;
    private Timeline gameLoop;
    private Canvas canvas;
    private GraphicsContext gc;

    // UI 요소
    private Label scoreLabel;
    private Label powerLabel;

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

        Label titleLabel = new Label("포탄 게임");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        Button startButton = new Button("게임 시작");
        startButton.setOnAction(e -> startGame());

        Button exitButton = new Button("종료");
        exitButton.setOnAction(e -> Platform.exit());

        menuLayout.getChildren().addAll(titleLabel, startButton, exitButton);
        Scene scene = new Scene(menuLayout, WORLD_WIDTH, WORLD_HEIGHT);

        primaryStage.setTitle("포탄 게임");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame() {
        // CannonGameWorld 객체 생성 및 초기화
        world = new CannonGameWorld(WORLD_WIDTH, WORLD_HEIGHT, GameMode.CLASSIC);
        world.addGlobalForce(new Vector2D(0, 9.8)); // 중력 추가
        world.createCannon(50, WORLD_HEIGHT - 50); // 대포 생성
        world.addTarget(new Target(500, 450, 50, 50, TargetType.WOODEN)); // 목표물 추가

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
                double dx = e.getX() - world.getCannon().getX();
                double dy = e.getY() - world.getCannon().getY();
                double angle = Math.atan2(dy, dx);
                world.getCannon().setAngle(angle);
            }
        });
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                // 일시정지 로직
            }
        });
    }

    private HBox createControlPanel() {
        HBox controlPanel = new HBox(20);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #333;");

        scoreLabel = new Label("점수: 0");
        scoreLabel.setTextFill(Color.WHITE);
        powerLabel = new Label("파워: 100");
        powerLabel.setTextFill(Color.WHITE);

        controlPanel.getChildren().addAll(scoreLabel, powerLabel);
        return controlPanel;
    }

    private void createGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            // 게임 루프 로직
            double deltaTime = 0.016; // 60 FPS
            world.update(deltaTime);
            world.render(gc);

            // UI 업데이트
            scoreLabel.setText("점수: " + world.getScore());
            powerLabel.setText("파워: " + (int)world.getCannon().getPower());
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}