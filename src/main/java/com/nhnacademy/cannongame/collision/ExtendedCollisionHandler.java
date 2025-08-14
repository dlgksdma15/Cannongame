package com.nhnacademy.cannongame.collision;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.MovableBall;
import com.nhnacademy.cannongame.box.Box;
import com.nhnacademy.cannongame.Vector2D;
import com.nhnacademy.cannongame.box.MovableBox;

import java.lang.reflect.Method;
import java.util.List;

public class ExtendedCollisionHandler {

    /**
     * 모든 Ball 쌍에 대한 충돌을 검사하고 처리합니다.
     *
     * @param balls 충돌을 검사할 Ball 리스트
     */
    public void checkBallToBallCollisions(List<Ball> balls) {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);

                // MovableBall이 아닌 경우 충돌 로직을 적용할 수 없으므로 건너뜁니다.
                if (!(ball1 instanceof MovableBall) || !(ball2 instanceof MovableBall)) {
                    continue;
                }

                MovableBall movableBall1 = (MovableBall) ball1;
                MovableBall movableBall2 = (MovableBall) ball2;

                // 충돌 조건 확인: 중심점 사이의 거리가 반지름의 합보다 작을 경우
                double distance = movableBall1.getCenter().distanceTo(movableBall2.getCenter());
                if (distance <= movableBall1.getRadius() + movableBall2.getRadius()) {
                    // 충돌 처리 로직 (탄성 충돌)
                    Vector2D v1 = movableBall1.getVelocity();
                    Vector2D v2 = movableBall2.getVelocity();

                    // 질량은 면적으로 가정합니다.
                    double m1 = movableBall1.getArea();
                    double m2 = movableBall2.getArea();

                    // 새로운 속도 계산 (1차원 충돌을 가정한 공식)
                    double newV1x = (v1.getX() * (m1 - m2) + 2 * m2 * v2.getX()) / (m1 + m2);
                    double newV2x = (v2.getX() * (m2 - m1) + 2 * m1 * v1.getX()) / (m1 + m2);

                    // y축 속도는 변하지 않는다고 가정
                    double newV1y = v1.getY();
                    double newV2y = v2.getY();

                    movableBall1.setVelocity(new Vector2D(newV1x, newV1y));
                    movableBall2.setVelocity(new Vector2D(newV2x, newV2y));
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

                // MovableBox가 아닌 경우 충돌 로직을 적용할 수 없으므로 건너뜁니다.
                if (!(box1 instanceof MovableBox) || !(box2 instanceof MovableBox)) {
                    continue;
                }

                MovableBox movableBox1 = (MovableBox) box1;
                MovableBox movableBox2 = (MovableBox) box2;

                // Box1과 Box2의 AABB 교차 여부 확인
                if (movableBox1.getBounds().intersects(movableBox2.getBounds())) {
                    // 충돌 시 처리 로직
                    // 두 박스의 속도를 반전시켜 튕겨나가게 합니다.
                    Vector2D v1 = movableBox1.getVelocity();
                    Vector2D v2 = movableBox2.getVelocity();

                    movableBox1.setVelocity(new Vector2D(-v1.getX(), -v1.getY()));
                    movableBox2.setVelocity(new Vector2D(-v2.getX(), -v2.getY()));
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
    public void checkBallToBoxCollisions(List<Ball> balls, List<Box> boxes) {
        for (Ball ball : balls) {
            for (Box box : boxes) {
                if (!(ball instanceof MovableBall)) continue;

                // 1. Box에서 Ball 중심까지 가장 가까운 점(closestPoint) 찾기
                Point closestPoint = getClosestPointOnBox(box, ball.getCenter());
                // 2. 가장 가까운 점과 Ball 중심 사이의 거리 계산
                double distance = ball.getCenter().distanceTo(closestPoint);

                // 3. 거리가 Ball 반지름보다 작으면 충돌
                if (distance < ball.getRadius()) {
                    // 4. 충돌한 면 판단
                    CollisionSide side = getCollisionSide(ball, box, closestPoint);

                    // 충돌한 면에 따라 반사 로직 구현
                    MovableBall movableBall = (MovableBall) ball;
                    Vector2D newVelocity = movableBall.getVelocity();

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
                    movableBall.setVelocity(newVelocity);
                }
            }
        }
    }


    private Point getClosestPointOnBox(Box box, Point ballCenter) {
        double closestX = Math.max(box.getX(), Math.min(ballCenter.getX(), box.getX() + box.getWidth()));
        double closestY = Math.max(box.getY(), Math.min(ballCenter.getY(), box.getY() + box.getHeight()));
        return new Point(closestX, closestY);
    }

    private CollisionSide getCollisionSide(Ball ball, Box box, Point closestPoint) {
        // 모서리 충돌 판단
        if (isCornerCollision(box, closestPoint)) {
            return CollisionSide.CORNER;
        }

        // 면 충돌 판단
        if (Math.abs(ball.getCenter().getX() - closestPoint.getX()) < 0.001) {
            // 좌우 면 충돌
            if (closestPoint.getX() == box.getX()) {
                return CollisionSide.LEFT;
            } else {
                return CollisionSide.RIGHT;
            }
        } else if (Math.abs(ball.getCenter().getY() - closestPoint.getY()) < 0.001) {
            // 상하 면 충돌
            if (closestPoint.getY() == box.getY()) {
                return CollisionSide.TOP;
            } else {
                return CollisionSide.BOTTOM;
            }
        }

        // 기타 (오류 처리 또는 모서리 충돌에 대한 추가 로직)
        return null;
    }

    private boolean isCornerCollision(Box box, Point closestPoint) {
        return (closestPoint.getX() == box.getX() || closestPoint.getX() == box.getX() + box.getWidth()) &&
                (closestPoint.getY() == box.getY() || closestPoint.getY() == box.getY() + box.getHeight());
    }

    public enum CollisionSide {
        TOP, BOTTOM, LEFT, RIGHT, CORNER
    }
    public CollisionSide detectCollisionSide(double px, double py, Box box) {
        double tolerance = 1.0; // 코너 충돌을 판별하기 위한 허용 오차

        // 박스의 경계값
        double minX = box.getX();
        double minY = box.getY();
        double maxX = box.getX() + box.getWidth();
        double maxY = box.getY() + box.getHeight();

        boolean isNearLeft = Math.abs(px - minX) < tolerance;
        boolean isNearRight = Math.abs(px - maxX) < tolerance;
        boolean isNearTop = Math.abs(py - minY) < tolerance;
        boolean isNearBottom = Math.abs(py - maxY) < tolerance;

        // 코너 충돌 판단
        if ((isNearLeft && isNearTop) || (isNearLeft && isNearBottom) ||
                (isNearRight && isNearTop) || (isNearRight && isNearBottom)) {
            return CollisionSide.CORNER;
        }

        // 면 충돌 판단
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

        // 박스 내부에 있을 경우, 혹은 면에 가깝지 않을 경우
        return null;
    }
    /**
     * 클래스에 특정 이름의 메서드가 존재하는지 확인합니다.
     * 리플렉션을 사용하여 동적으로 메서드를 찾습니다.
     *
     * @param methodName 찾고자 하는 메서드의 이름
     * @return 해당 메서드가 존재하면 true, 그렇지 않으면 false
     */
    public boolean hasMethod(String methodName) {
        try {
            // 클래스에 선언된 모든 메서드를 가져옵니다.
            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            System.err.println("리플렉션 보안 예외: " + e.getMessage());
        }
        return false;
    }
}