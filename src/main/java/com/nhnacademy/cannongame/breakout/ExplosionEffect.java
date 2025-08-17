package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Paintable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExplosionEffect implements Paintable {
    private final double centerX;
    private final double centerY;
    private final Color color;
    private final double duration; // 폭발 지속 시간(초)
    private double elapsedTime; // 경과 시간(초)
    private double currentRadius;
    private final double maxRadius;
    private boolean finished;

    public ExplosionEffect(double centerX, double centerY, Color color, double duration, double maxRadius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
        this.duration = duration;
        this.maxRadius = maxRadius;
        this.elapsedTime = 0;
        this.currentRadius = 0;
        this.finished = false;
    }

    @Override
    public void paint(GraphicsContext gc) {
        if (finished) {
            return;
        }

        // 경과 시간에 따라 투명도(alpha)를 조절
        double alpha = 1.0 - (elapsedTime / duration);
        if (alpha < 0) {
            alpha = 0;
        }

        gc.setFill(Color.rgb(
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255),
                alpha
        ));

        // 폭발 원 그리기
        gc.fillOval(
                centerX - currentRadius,
                centerY - currentRadius,
                currentRadius * 2,
                currentRadius * 2
        );
    }

    /**
     * 폭발 효과 상태를 업데이트합니다.
     * @param deltaTime 이전 프레임으로부터의 시간(초)
     */
    public void update(double deltaTime) {
        if (finished) {
            return;
        }

        elapsedTime += deltaTime;

        // 경과 시간에 비례하여 반지름을 증가시킵니다.
        currentRadius = (elapsedTime / duration) * maxRadius;

        if (elapsedTime >= duration) {
            finished = true;
        }
    }

    /**
     * 폭발 효과가 끝났는지 여부를 반환합니다.
     * @return 폭발 효과가 끝났으면 true, 아니면 false
     */
    public boolean isFinished() {
        return finished;
    }
}