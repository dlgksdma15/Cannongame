package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Ball;

import java.util.ArrayList;
import java.util.List;

public class BreakoutGame {
    private BreakoutPaddle paddle; // 패들 객체
    private Ball ball; // 공 객체 (나중에 여러 개 가능)
    private List<Brick> bricks; // 벽돌 리스트
//    private List<PowerUp> powerUps; // 활성 파워업 리스트
    private GameState gameState; // 현재 게임 상태
    private int score; // 현재 점수
    private int lives; // 남은 생명
    private int level; // 현재 레벨

    // 상수
    public static final int INITIAL_LIVES = 3;
    public static final int POINTS_PER_BRICK = 10;
    public static final double BALL_SPEED_INCREMENT = 1.1;

    // 생성자
    public BreakoutGame() {
        this.bricks = new ArrayList<>();
//        this.powerUps = new ArrayList<>();
        this.gameState = GameState.RUNNING; // 초기 게임 상태
        this.lives = INITIAL_LIVES;
        this.score = 0;
        this.level = 1;
        // 패들, 공, 벽돌 등을 초기화하는 메소드를 호출
        initializeGame();
    }

    private void initializeGame() {
        // 게임 초기화 로직
        // 예: paddle = new Paddle(...);
        // 예: ball = new Ball(...);
        // 예: loadBricksForLevel(level);
    }

}
