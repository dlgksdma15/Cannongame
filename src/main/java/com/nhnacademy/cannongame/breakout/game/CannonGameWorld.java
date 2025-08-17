package com.nhnacademy.cannongame.breakout.game;

import com.nhnacademy.cannongame.breakout.PhysicsWorld;
import com.nhnacademy.cannongame.simpleworld.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CannonGameWorld extends PhysicsWorld {
    // 필드
    private final int TILE_COLS = 5;
    private final int TILE_ROWS = 3;
    private final double TILE_WIDTH = 100;
    private final double TILE_HEIGHT = 50;
    private final double PADDING = 10;
    private Cannon cannon;
    private final List<Projectile> projectiles;
    private final List<Target> targets;
    private final List<Box> obstacles;
    private int score;
    private int shotsRemaining;
    private final GameMode gameMode;
    private int currentLevel;

    public CannonGameWorld(double width, double height, GameMode gameMode) {
        super(width, height);
        this.gameMode = gameMode;
        this.projectiles = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.score = 0;
        this.currentLevel = 1;
    }

    // 누락된 메소드들 추가

    public void createCannon(double x, double y) {
        this.cannon = new Cannon(x, y);
    }

    public void addTarget(Target target) {
        targets.add(target);
    }

    public Cannon getCannon() {
        return cannon;
    }

    public int getScore() {
        return score;
    }

    // 레벨 로드 (간단한 예시로 구현)
    public void loadLevel(int levelData) {
        // 기존 객체 초기화
        projectiles.clear();
        targets.clear();
        obstacles.clear();

        // 중첩 루프를 사용하여 목표물 격자 생성
        for (int row = 0; row < TILE_ROWS; row++) {
            for (int col = 0; col < TILE_COLS; col++) {
                double x = PADDING + col * (TILE_WIDTH + PADDING);
                double y = PADDING * 2 + row * (TILE_HEIGHT + PADDING);

                // 다양한 타입의 목표물을 생성할 수 있도록 로직 추가
                addTarget(new Target(x, y, TILE_WIDTH, TILE_HEIGHT, TargetType.WOODEN));
            }
        }

        // 게임 모드에 따른 포탄 수 설정
        if (gameMode == GameMode.LIMITED_SHOTS) {
            this.shotsRemaining = 10;
        } else {
            this.shotsRemaining = Integer.MAX_VALUE;
        }
    }

    // 발사 (isCharging 메소드 호출로 수정)
    // 올바르게 수정된 fire() 메소드
    // 수정된 fire() 메소드
    // 수정된 fire() 메소드
    public void fire() {
        // 포탄 수만 확인하고, 충전 상태 확인은 Cannon 클래스에 위임
        if (shotsRemaining > 0) {
            Projectile projectile = getCannon().fire();
            if (projectile != null) { // 포탄이 성공적으로 발사되었는지 확인
                projectiles.add(projectile);
                super.addObject(projectile);
                shotsRemaining--;
            }
        }
    }

    // 업데이트 오버라이드
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        // handleExplosions(); // 폭발 처리는 필요 시 주석 해제
        removeDestroyedObjects();
        // updateScore(); // 점수 업데이트 로직도 필요 시 추가
        checkWinCondition();
    }

    // 화면에 객체들을 그리는 render 메소드 추가
    public void render(GraphicsContext gc) {
        // 배경 그리기 (필요 시)
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);

        if (cannon != null) {
            cannon.paint(gc);
        }
        for (Target target : targets) {
            target.paint(gc);
        }
        for (Projectile projectile : projectiles) {
            projectile.paint(gc);
        }
    }

    // 폭발 처리 (기존 코드)
    private void handleExplosions() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            if (projectile.hasExploded()) {
                for (Target target : targets) {
                    double distance = Math.sqrt(
                            Math.pow(target.getX() - projectile.getX(), 2) +
                                    Math.pow(target.getY() - projectile.getY(), 2)
                    );

                    if (distance <= projectile.getExplosionRadius()) {
                        double damageRatio = 1 - (distance / projectile.getExplosionRadius());
                        double damage = damageRatio * 2;
                        target.takeDamage(damage);
                    }
                }
                iterator.remove();
            }
        }
    }

    // 파괴된 객체 제거
    private void removeDestroyedObjects() {
        // targets.removeIf(Target::isDestroyed);
        // projectiles.removeIf(p -> p.isOutOfBounds(getWidth(), getHeight())); // 필요 시 추가
    }

    // 점수 업데이트 (간단한 예시로 구현)
    private void updateScore() {
        int pointsGained = 0;
        Iterator<Target> iterator = targets.iterator();
        while (iterator.hasNext()) {
            Target target = iterator.next();
            if (target.isDestroyed()) {
                pointsGained += target.getPoints();
                iterator.remove();
            }
        }
        score += pointsGained;
    }

    // 승리/패배 조건 확인
    private void checkWinCondition() {
        if (targets.isEmpty()) {
            System.out.println("승리!");
        }
        if (gameMode == GameMode.LIMITED_SHOTS && shotsRemaining <= 0 && projectiles.isEmpty()) {
            System.out.println("패배! 포탄을 모두 소진했습니다.");
        }
    }

    public int getShotsRemaining() {
        return shotsRemaining;
    }
}