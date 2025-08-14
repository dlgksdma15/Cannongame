package com.nhnacademy.cannongame.collision;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.MovableBall;

// 두 공 사이의 충돌을 감지하고 처리하는 클래스입니다:
public final class BallCollision {

    private BallCollision() {
        // 정적 메서드만 제공하는 유틸리티 클래스이므로 인스턴스화 방지
    }

    /**
     * 두 공이 충돌했는지 확인합니다.
     *
     * @param ball1 첫 번째 공
     * @param ball2 두 번째 공
     * @return 충돌했다면 true, 그렇지 않으면 false
     */
    public static boolean areColliding(Ball ball1, Ball ball2) {
        if (ball1 == null || ball2 == null) {
            return false;
        }
        double distance = ball1.getCenter().distanceTo(ball2.getCenter()); // 거리 계산
        double radiusSum = ball1.getRadius() + ball2.getRadius(); // 반지름 합 계산
        return distance < radiusSum; // 50 < 60 -> 겹치지 않으므로 false 반환
    }

    /**
     * 두 공의 탄성 충돌을 처리합니다.
     * 공이 MovableBall일 경우에만 속도가 변경됩니다.
     *
     * @param ball1 첫 번째 공
     * @param ball2 두 번째 공
     */
    public static void resolveElasticCollision(Ball ball1, Ball ball2) {
        if (!(ball1 instanceof MovableBall) || !(ball2 instanceof MovableBall)) {
            return; // 둘 다 움직일 수 있는 공이 아니면 충돌을 처리하지 않음
        }

        MovableBall mBall1 = (MovableBall) ball1;
        MovableBall mBall2 = (MovableBall) ball2;

        // 1. 충돌 방향 벡터 계산
        Vector2D collisionNormal = (Vector2D) mBall2.getCenter().subtract(mBall1.getCenter()).normalize();

        // 2. 상대 속도 계산
        Vector2D relativeVelocity = mBall2.getVelocity().subtract(mBall1.getVelocity());

        // 3. 충돌 방향으로의 상대 속도
        double velocityAlongNormal = relativeVelocity.dot(collisionNormal);

        // 두 공이 서로 멀어지고 있다면 충돌 처리 안함
        if (velocityAlongNormal > 0) {
            return;
        }

        // 질량은 1로 가정 (getMass()가 없으므로)
        double mass1 = 1.0;
        double mass2 = 1.0;

        // 4. 충격량(impulse) 계산
        double impulseScalar = -(2.0 * velocityAlongNormal) / (mass1 + mass2);
        Vector2D impulse = collisionNormal.multiply(impulseScalar);

        // 5. 각 공의 새로운 속도 계산 및 적용
        Vector2D newVelocity1 = mBall1.getVelocity().add(impulse.multiply(1.0 / mass1));
        Vector2D newVelocity2 = mBall2.getVelocity().subtract(impulse.multiply(1.0 / mass2));

        mBall1.setVelocity(newVelocity1);
        mBall2.setVelocity(newVelocity2);

        // 6. 겹침 해결
        separateBalls(mBall1, mBall2);
    }

    /**
     * 겹쳐진 두 공을 분리합니다.
     *
     * @param ball1 첫 번째 공
     * @param ball2 두 번째 공
     */
    public static void separateBalls(Ball ball1, Ball ball2) {
        double distance = ball1.getCenter().distanceTo(ball2.getCenter());
        double overlap = ball1.getRadius() + ball2.getRadius() - distance;

        if (overlap > 0) {
            // 겹친 거리의 절반만큼 각 공을 밀어냄
            Vector2D separationVector = ball2.getCenter().subtract(ball1.getCenter()).normalize().multiply(overlap / 2);

            Point newCenter1 = ball1.getCenter().add(separationVector.multiply(-1));
            Point newCenter2 = ball2.getCenter().add(separationVector);

            ball1.moveTo(newCenter1);
            ball2.moveTo(newCenter2);
        }
    }
}