package com.nhnacademy.cannongame.breakout.game;

import com.nhnacademy.cannongame.bounds.Bounds;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import com.nhnacademy.cannongame.breakout.Breakable;
import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import com.nhnacademy.cannongame.simpleworld.Paintable;
import com.nhnacademy.cannongame.simpleworld.Boundable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class Target implements Paintable, Collidable, Breakable {
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    protected int points;
    private final TargetType type;
    private double health;
    private final double maxHealth;
    private boolean isDestroyed;
    private final Random random;

    public Target(double x, double y, double width, double height, TargetType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.points = type.getPoints();
        this.maxHealth = 100 * type.getDurability(); // 내구도에 따른 최대 체력
        this.health = maxHealth;
        this.isDestroyed = false;
        long seed = (long) (x * 1000 + y);
        this.random = new Random(seed);
    }

    // Paintable 인터페이스 구현
    @Override
    public void paint(GraphicsContext gc) {
        if (isDestroyed) { return; }

        double healthRatio = health / maxHealth;
        Color baseColor = type.getColor();
        Color displayColor = baseColor.interpolate(Color.BLACK, 1.0 - healthRatio);

        // 손상도에 따른 색상 변화
        gc.setFill(displayColor);
        gc.fillRect(x, y, width, height);

        // 테두리
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, width, height);

        // TNT는 텍스트 표시
        if (type == TargetType.TNT) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 16));
            gc.fillText("TNT", x + width / 4, y + height / 2);
        }

        // 손상 50% 이하 시 균열 표시
        if (healthRatio <= 0.5) {
            drawCracks(gc);
        }
    }

    private void drawCracks(GraphicsContext gc) {
        Random tempRandom = new Random(random.nextLong());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        int numCracks = (int) (10 * (1.0 - (health / maxHealth)));

        for (int i = 0; i < numCracks; i++) {
            double startX = x + tempRandom.nextDouble() * width;
            double startY = y + tempRandom.nextDouble() * height;
            double endX = x + tempRandom.nextDouble() * width;
            double endY = y + tempRandom.nextDouble() * height;
            gc.strokeLine(startX, startY, endX, endY);
        }
    }

    // Collidable 인터페이스 구현
    @Override
    public Bounds getBounds() {
        return new RectangleBounds(x, y, width, height);
    }

    @Override
    public boolean isColliding(Boundable other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public void handleCollision(Collidable other) {
        if (other instanceof Projectile) {
            takeDamage(100); // 포탄 종류에 따라 다른 데미지 부여 가능
        }
    }

    @Override
    public CollisionAction getCollisionAction() {
        return CollisionAction.PASS;
    }

    @Override
    public int getPoints() {
        return points;
    }

    // Breakable 인터페이스 구현
    @Override
    public void hit() {
        // hit() 메소드는 takeDamage()를 호출하여 포탄 종류에 따른 데미지를 처리합니다.
        // 예를 들어 Projectile의 데미지 값을 전달하도록 변경할 수 있습니다.
        takeDamage(100);
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

//    @Override
//    public int getPoints() {
//        return points;
//    }

    // 핵심 메서드
    public void takeDamage(double damage) {
        if (isDestroyed) { return; }
        health -= damage;
        if (health <= 0) {
            isDestroyed = true;
            if (type == TargetType.TNT) {
                // TNT 폭발 로직 호출
                // triggerExplosion();
            }
        }
    }

    private void triggerExplosion() {
        // 주변 객체에 피해를 주는 로직
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}