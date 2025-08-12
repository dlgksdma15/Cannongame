package com.nhnacademy.cannongame;

import com.nhnacademy.cannongame.ball.BoundedBall;

// 벽과의 충돌을 감지하고 처리하는 유틸리티 클래스입니다:
public class CollisionDetector {
    public enum Wall{
        LEFT, RIGHT, TOP, BOTTOM
    }
    private CollisionDetector(){

    }
    public static class WallCollision{
        private final Wall wall;
        private final double penetration;


        public WallCollision(Wall wall, double penetration){
            this.wall = wall;
            this.penetration = penetration;
        }

        public Wall getWall() {
            return wall;
        }

        public double getPenetration() {
            return penetration;
        }
    }
    /**
     * 공이 주어진 경계(벽)와 충돌했는지 확인합니다.
     * 여러 벽과 동시에 충돌하는 경우, 가장 깊이 침투한 충돌 정보를 반환합니다.
     *
     * @param ball  충돌을 검사할 공
     * @param minX  경계의 최소 x좌표
     * @param minY  경계의 최소 y좌표
     * @param maxX  경계의 최대 x좌표
     * @param maxY  경계의 최대 y좌표
     * @return      충돌 정보를 담은 WallCollision 객체. 충돌이 없으면 null을 반환합니다.
     */

    public static WallCollision checkWallCollision(BoundedBall ball, double minX, double minY, double maxX, double maxY) {
        WallCollision deepestCollision = null;

        // 왼쪽 벽
        double penetrationLeft = minX - (ball.getCenter().getX() - ball.getRadius());
        if (penetrationLeft > 0) {
            deepestCollision = new WallCollision(Wall.LEFT, penetrationLeft);
        }

        // 오른쪽 벽
        double penetrationRight = (ball.getCenter().getX() + ball.getRadius()) - maxX;
        if (penetrationRight > 0 && (deepestCollision == null || penetrationRight > deepestCollision.getPenetration())) {
            deepestCollision = new WallCollision(Wall.RIGHT, penetrationRight);
        }

        // 위쪽 벽
        double penetrationTop = minY - (ball.getCenter().getY() - ball.getRadius());
        if (penetrationTop > 0 && (deepestCollision == null || penetrationTop > deepestCollision.getPenetration())) {
            deepestCollision = new WallCollision(Wall.TOP, penetrationTop);
        }

        // 아래쪽 벽
        double penetrationBottom = (ball.getCenter().getY() + ball.getRadius()) - maxY;
        if (penetrationBottom > 0 && (deepestCollision == null || penetrationBottom > deepestCollision.getPenetration())) {
            deepestCollision = new WallCollision(Wall.BOTTOM, penetrationBottom);
        }

        return deepestCollision;
    }

    /**
     * 충돌한 벽에 따라 속도 반전
     * LEFT/RIGHT: x 속도 반전
     * TOP/BOTTOM: y 속도 반전
     */
    public static void resolveWallCollision(BoundedBall ball, WallCollision collision) {
        final double restitution = 0.8; // 반발 계수
        Point center = ball.getCenter();
        Vector2D velocity = ball.getVelocity();

        switch (collision.getWall()) {
            case LEFT:
                // 1. 위치 보정: 침투한 깊이만큼 공을 오른쪽으로 밀어냄
                ball.moveTo(new Point(center.getX() + collision.getPenetration(), center.getY()));
                // 2. 속도 반사: x축 속도의 방향을 반대로 하고 반발 계수 적용
                ball.setVelocity(new Vector2D(-velocity.getX() * restitution, velocity.getY()));
                break;
            case RIGHT:
                ball.moveTo(new Point(center.getX() - collision.getPenetration(), center.getY()));
                ball.setVelocity(new Vector2D(-velocity.getX() * restitution, velocity.getY()));
                break;
            case TOP:
                ball.moveTo(new Point(center.getX(), center.getY() + collision.getPenetration()));
                ball.setVelocity(new Vector2D(velocity.getX(), -velocity.getY() * restitution));
                break;
            case BOTTOM:
                ball.moveTo(new Point(center.getX(), center.getY() - collision.getPenetration()));
                ball.setVelocity(new Vector2D(velocity.getX(), -velocity.getY() * restitution));
                break;
        }
    }


}
