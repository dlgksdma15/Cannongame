package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.*;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreakoutWorld {
    // 필드
    private double width;
    private double height;
    private final List<UnbreakableBrick> walls;
    private final List<Breakable> bricks;
    private final List<BreakoutBall> balls;
    private BreakoutPaddle paddle;
    private final List<PowerUp> powerUps;
    private final List<ExplosionEffect> explosions;

    // 생성자
    public BreakoutWorld(double width, double height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.bricks = new ArrayList<>();
        this.balls = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.explosions = new ArrayList<>();
        // 여기에 벽(walls)을 생성하는 로직을 추가합니다.
        createBoundaries();
    }

    private void createBoundaries() {
        // 상단 벽
        walls.add(new UnbreakableBrick(0, -10, width, 10));
        // 하단 벽 (공을 놓쳤을 때)
        walls.add(new UnbreakableBrick(0, height, width, 10));
        // 좌측 벽
        walls.add(new UnbreakableBrick(-10, 0, 10, height));
        // 우측 벽
        walls.add(new UnbreakableBrick(width, 0, 10, height));
    }

    // 객체 추가 메서드 (이전과 동일)
    public void addWall(UnbreakableBrick wall) { walls.add(wall); }
    public void addBrick(Breakable brick) { bricks.add(brick); }
    public void addBall(BreakoutBall ball) { balls.add(ball); }
    public void addPowerUp(PowerUp powerUp) { powerUps.add(powerUp); }
    public void addExplosion(ExplosionEffect explosion) { explosions.add(explosion); }
    public void setPaddle(BreakoutPaddle paddle) { this.paddle = paddle; }

    // 모든 객체를 업데이트하는 메소드
    public void update(double deltaTime) {
        // 객체 이동
        if (paddle != null) {
            paddle.move(deltaTime);
        }
        for (BreakoutBall ball : balls) {
            ball.move(deltaTime);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.move(deltaTime);
        }

        handleCollisions();
        removeDestroyedObjects();
        updateExplosions(deltaTime);
    }

    // 모든 객체를 그리는 메소드 (이전과 동일)
    public void render(GraphicsContext gc) {
        for (UnbreakableBrick wall : walls) {
            wall.paint(gc);
        }
        for (Breakable brick : bricks) {
            if (!brick.isDestroyed()) {
                ((StaticObject) brick).paint(gc);
            }
        }
        for (BreakoutBall ball : balls) {
            ball.paint(gc);
        }
        if (paddle != null) {
            paddle.paint(gc);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.paint(gc);
        }
        for (ExplosionEffect explosion : explosions) {
            explosion.paint(gc);
        }
    }

    // 충돌 처리 통합
    private void handleCollisions() {
        // 공과 벽(UnbreakableBrick) 충돌
        for (BreakoutBall ball : balls) {
            for (UnbreakableBrick wall : walls) {
                if (ball.isColliding(wall)) {
                    ball.handleCollision(wall);
                }
            }
        }

        // 공과 벽돌(Breakable) 충돌
        for (BreakoutBall ball : balls) {
            Iterator<Breakable> iterator = bricks.iterator();
            while (iterator.hasNext()) {
                Breakable brick = iterator.next();
                if (ball.isColliding((Collidable) brick)) {
                    brick.hit();
                    ball.handleCollision((Collidable) brick);

                    if (brick.isDestroyed()) {
                        handleBrickDestruction(brick);
                    }
                }
            }
        }

        // 공과 패들 충돌
        if (paddle != null) {
            for (BreakoutBall ball : balls) {
                if (ball.isColliding(paddle)) {
                    paddle.calculateBounce(ball);
                }
            }
        }

        // 파워업과 패들 충돌
        if (paddle != null) {
            for (PowerUp powerUp : powerUps) {
                if (powerUp.isColliding(paddle)) {
                    powerUp.handleCollision(paddle);
                }
            }
        }
    }

    // 벽돌 파괴 시 특수 효과 처리
    private void handleBrickDestruction(Breakable brick) {
        if (brick instanceof Exploding) {
            // 수정된 Exploding 인터페이스에 맞게 bricks 리스트를 전달
            ((Exploding) brick).triggerExplosion(bricks);
        }

        if (brick instanceof PowerUpProvider) {
            PowerUp newPowerUp = ((PowerUpProvider) brick).dropPowerUp();
            if (newPowerUp != null) {
                addPowerUp(newPowerUp);
            }
        }
    }

    // 파괴된 객체를 리스트에서 제거
    private void removeDestroyedObjects() {
        bricks.removeIf(Breakable::isDestroyed);
        powerUps.removeIf(PowerUp::isCollected);
        balls.removeIf(ball -> ball.isLost(this.height));
        explosions.removeIf(ExplosionEffect::isFinished);
    }

    // 폭발 효과 업데이트
    private void updateExplosions(double deltaTime) {
        for (ExplosionEffect explosion : explosions) {
            explosion.update(deltaTime);
        }
    }

    // getter 메서드 (필요에 따라 추가)
    public List<Breakable> getBricks() {
        return bricks;
    }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public BreakoutPaddle getPaddle() {
        return paddle;
    }

    public List<UnbreakableBrick> getWalls() {
        return walls;
    }

    public List<BreakoutBall> getBalls() {
        return balls;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public List<ExplosionEffect> getExplosions() {
        return explosions;
    }
}