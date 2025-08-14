package com.nhnacademy.cannongame.collision;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.MovableBall;
import com.nhnacademy.cannongame.box.Box;

import java.util.List;

public class CollisionHandler {

    public enum CollisionSide {
        TOP, BOTTOM, LEFT, RIGHT, CORNER
    }

    /**
     * 모든 Ball 쌍에 대한 충돌을 검사하고 처리합니다.
     * 탄성 충돌 로직을 적용하여 서로 반발하도록 합니다.
     *
     * @param balls 충돌을 검사할 MovableBall 리스트
     */
    public void checkBallToBallCollisions(List<MovableBall> balls) {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                MovableBall ball1 = balls.get(i);
                MovableBall ball2 = balls.get(j);

                double distance = ball1.getCenter().distanceTo(ball2.getCenter());
                if (distance <= ball1.getRadius() + ball2.getRadius()) {
                    // 충돌 처리 로직 (반사) - 운동량 보존
                    Vector2D v1 = ball1.getVelocity();
                    Vector2D v2 = ball2.getVelocity();
                    double m1 = ball1.getArea(); // 질량 대신 면적 사용
                    double m2 = ball2.getArea();

                    // 새로운 속도 계산 (1차원 충돌 가정)
                    double newV1x = (v1.getX() * (m1 - m2) + 2 * m2 * v2.getX()) / (m1 + m2);
                    double newV2x = (v2.getX() * (m2 - m1) + 2 * m1 * v1.getX()) / (m1 + m2);

                    double newV1y = (v1.getY() * (m1 - m2) + 2 * m2 * v2.getY()) / (m1 + m2);
                    double newV2y = (v2.getY() * (m2 - m1) + 2 * m1 * v1.getY()) / (m1 + m2);

                    ball1.setVelocity(new Vector2D(newV1x, newV1y));
                    ball2.setVelocity(new Vector2D(newV2x, newV2y));
                }
            }
        }
    }

    /**
     * 모든 Box 쌍에 대한 충돌을 검사하고 처리합니다.
     *
     * @param boxes 충돌을 검사할 Box 리스트
     */
    public void checkBoxToBoxCollisions(List<Box> boxes) {
        // AABB(Axis-Aligned Bounding Box) 충돌 검사
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                Box box1 = boxes.get(i);
                Box box2 = boxes.get(j);

                if (box1.getBounds().intersects(box2.getBounds())) {
                    // 충돌 시 처리 로직 구현 (예: 정지 또는 밀어내기)
                    // 여기서는 복잡한 로직을 생략하고, 충돌이 발생했다는 것만 가정합니다.
                }
            }
        }
    }

    /**
     * Ball과 Box 간의 충돌을 검사하고 처리합니다.
     *
     * @param balls 충돌을 검사할 Ball 리스트
     * @param boxes 충돌을 검사할 Box 리스트
     */
    public void checkBallToBoxCollisions(List<MovableBall> balls, List<Box> boxes) {
        for (MovableBall ball : balls) {
            for (Box box : boxes) {
                // 1. Box에서 Ball 중심까지 가장 가까운 점(closestPoint) 찾기
                Point closestPoint = getClosestPointOnBox(box, ball.getCenter());
                // 2. 가장 가까운 점과 Ball 중심 사이의 거리 계산
                double distance = ball.getCenter().distanceTo(closestPoint);

                // 3. 거리가 Ball 반지름보다 작으면 충돌
                if (distance < ball.getRadius()) {
                    // 4. 충돌한 면 판단
                    CollisionSide side = detectCollisionSide(ball.getCenter().getX(), ball.getCenter().getY(), box);

                    // 충돌한 면에 따라 반사 로직 구현
                    Vector2D newVelocity = ball.getVelocity();

                    switch (side) {
                        case TOP:
                        case BOTTOM:
                            // y축 속도 반전
                            newVelocity = new Vector2D(newVelocity.getX(), -newVelocity.getY());
                            break;
                        case LEFT:
                        case RIGHT:
                            // x축 속도 반전
                            newVelocity = new Vector2D(-newVelocity.getX(), newVelocity.getY());
                            break;
                        case CORNER:
                            // 양축 속도 반전
                            newVelocity = new Vector2D(-newVelocity.getX(), -newVelocity.getY());
                            break;
                    }
                    ball.setVelocity(newVelocity);
                }
            }
        }
    }

    /**
     * Box에서 특정 좌표(ballCenter)까지 가장 가까운 점을 찾습니다.
     *
     * @param box        Box 객체
     * @param ballCenter 검사할 좌표
     * @return Box 경계의 가장 가까운 점
     */
    private Point getClosestPointOnBox(Box box, Point ballCenter) {
        double closestX = Math.max(box.getX(), Math.min(ballCenter.getX(), box.getX() + box.getWidth()));
        double closestY = Math.max(box.getY(), Math.min(ballCenter.getY(), box.getY() + box.getHeight()));
        return new Point(closestX, closestY);
    }

    /**
     * 특정 좌표가 Box의 어느 면에 가장 가까운지 감지하는 메서드.
     *
     * @param px  충돌이 발생한 점의 X 좌표
     * @param py  충돌이 발생한 점의 Y 좌표
     * @param box 충돌이 감지된 Box 객체
     * @return 충돌이 발생한 면을 나타내는 CollisionSide 열거형 값
     */
    public CollisionSide detectCollisionSide(double px, double py, Box box) {
        double tolerance = 1.0;

        double minX = box.getX();
        double minY = box.getY();
        double maxX = box.getX() + box.getWidth();
        double maxY = box.getY() + box.getHeight();

        boolean isNearLeft = Math.abs(px - minX) < tolerance;
        boolean isNearRight = Math.abs(px - maxX) < tolerance;
        boolean isNearTop = Math.abs(py - minY) < tolerance;
        boolean isNearBottom = Math.abs(py - maxY) < tolerance;

        if ((isNearLeft && isNearTop) || (isNearLeft && isNearBottom) ||
                (isNearRight && isNearTop) || (isNearRight && isNearBottom)) {
            return CollisionSide.CORNER;
        }

        if (isNearLeft) {
            return CollisionSide.LEFT;
        }
        if (isNearRight) {
            return CollisionSide.RIGHT;
        }
        if (isNearTop) {
            return CollisionSide.TOP;
        }
        if (isNearBottom) {
            return CollisionSide.BOTTOM;
        }

        return null;
    }
}

