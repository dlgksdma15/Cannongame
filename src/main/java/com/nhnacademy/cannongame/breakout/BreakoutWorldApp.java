package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreakoutWorldApp extends Application {
    // 필드
    private BreakoutWorld world;
    private GameState gameState;
    private int score;
    private int lives;
    private int level;
    private Label scoreLabel;
    private Label livesLabel;
    private AnimationTimer gameLoop;

    private static final double WORLD_WIDTH = 800;
    private static final double WORLD_HEIGHT = 600;

    // 상수
    public static final int INITIAL_LIVES = 3;
    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLS = 10;
    private static final double BRICK_WIDTH = 70;
    private static final double BRICK_HEIGHT = 20;
    private static final double PADDING = 5;

    private long lastFrameTime = 0;

    @Override
    public void start(Stage primaryStage) {
        // UI 구성
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        BorderPane gameLayout = new BorderPane();
        Canvas canvas = new Canvas(WORLD_WIDTH, WORLD_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        VBox infoPanel = new VBox(5);
        infoPanel.setAlignment(Pos.CENTER);
        scoreLabel = new Label("Score: 0");
        livesLabel = new Label("Lives: " + INITIAL_LIVES);
        infoPanel.getChildren().addAll(scoreLabel, livesLabel);

        gameLayout.setCenter(canvas);
        gameLayout.setTop(infoPanel);
        root.getChildren().add(gameLayout);

        Scene scene = new Scene(root, WORLD_WIDTH, WORLD_HEIGHT + 50);

        // 게임 초기화
        initializeGame();

        // 이벤트 핸들러 등록
        setupInputHandlers(scene);

        // 게임 루프 시작
        createGameLoop(gc);
        gameLoop.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Breakout");
        primaryStage.show();
    }

    private void initializeGame() {
        world = new BreakoutWorld(800,600);
        gameState = GameState.PLAYING;
        score = 0;
        lives = INITIAL_LIVES;
        level = 1;

        initializeLevel(level);
    }

    private void initializeLevel(int levelNumber) {
        world.getBricks().clear();

        // 패들 생성
        BreakoutPaddle paddle = new BreakoutPaddle(WORLD_WIDTH / 2 - 40, WORLD_HEIGHT - 30, 80, 10, Color.BLACK, CollisionAction.BOUNCE);
        world.setPaddle(paddle);

        // 공 생성 및 속도 설정
        BreakoutBall ball = new BreakoutBall(WORLD_WIDTH / 2, WORLD_HEIGHT - 40, 5, Color.RED, CollisionAction.BOUNCE);
        ball.setDx(150); // X축 속도 설정
        ball.setDy(-150); // Y축 속도 설정 (위로 이동)
        world.addBall(ball);

        // 벽돌 생성
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLS; col++) {
                Breakable brick = createBrickForLevel(col, row, levelNumber);
                if (brick != null) {
                    world.addBrick(brick);
                }
            }
        }
    }

    private Breakable createBrickForLevel(int col, int row, int levelNumber) {
        double x = PADDING + col * (BRICK_WIDTH + PADDING);
        double y = PADDING * 4 + row * (BRICK_HEIGHT + PADDING);

        // 난이도에 따른 벽돌 유형 선택
        Random random = new Random();
        if (levelNumber >= 3 && random.nextInt(7) == 0) {
            return new ExplodingBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT, BrickType.EXPLOSIVE);

        } else if (levelNumber >= 2 && row < 2 && random.nextInt(3) == 0) {
            return new MultiHitBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT,BrickType.MULTI_HIT);
        } else if (random.nextInt(5) == 0) {
            return new PowerUpBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        } else {
            return new SimpleBrick(x, y, BRICK_WIDTH, BRICK_HEIGHT,BrickType.SIMPLE);
        }
    }

    private void setupInputHandlers(Scene scene) {
        scene.setOnMouseMoved(e -> world.getPaddle().setTargetX(e.getX()));
    }

    private void createGameLoop(GraphicsContext gc) {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime == 0) {
                    lastFrameTime = now;
                    return;
                }
                double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;

                if (gameState == GameState.PLAYING) {
                    world.update(deltaTime);
                    checkBallLost();
                    checkLevelComplete();
                    updateUI();

                    // 화면 지우고 다시 그리기
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
                    world.render(gc);
                }
            }
        };
    }

    private void checkBallLost() {
        List<BreakoutBall> ballsToRemove = new ArrayList<>();
        for (BreakoutBall ball : world.getBalls()) {
            if (ball.isLost(WORLD_HEIGHT)) {
                ballsToRemove.add(ball);
            }
        }

        if (!ballsToRemove.isEmpty()) {
            world.getBalls().removeAll(ballsToRemove);
            if (world.getBalls().isEmpty()) {
                lives--;
                if (lives > 0) {
                    world.addBall(new BreakoutBall(WORLD_WIDTH / 2, WORLD_HEIGHT - 40, 5));
                } else {
                    gameState = GameState.GAME_OVER;
                }
            }
        }
    }

    private void checkLevelComplete() {
        if (world.getBricks().isEmpty()) {
            level++;
            // 다음 레벨 초기화 (레벨 데이터 로딩 로직 필요)
            initializeLevel(level);
        }
    }

    private void updateUI() {
        // 점수 업데이트 로직이 필요
        livesLabel.setText("Lives: " + lives);
        // scoreLabel.setText("Score: " + score);
    }
}