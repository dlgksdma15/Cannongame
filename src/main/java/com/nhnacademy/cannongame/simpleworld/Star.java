//package com.nhnacademy.cannongame.simpleworld;
//import com.nhnacademy.cannongame.bounds.RectangleBounds;
//import javafx.geometry.Bounds;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//public class Star implements Paintable, Movable, Collidable {
//    // 필드
//    private double x, y, outerRadius, innerRadius;
//    private double dx, dy;
//    private Color color;
//    private CollisionAction collisionAction;
//    private double rotation; // 회전 각도
//
//    // 생성자
//    public Star(double x, double y, double outerRadius, double innerRadius, Color color, CollisionAction collisionAction) {
//        this.x = x;
//        this.y = y;
//        this.outerRadius = outerRadius;
//        this.innerRadius = innerRadius;
//        this.color = color;
//        this.collisionAction = collisionAction;
//    }
//
//    // Paintable 인터페이스 구현
//    @Override
//    public void paint(GraphicsContext gc) {
//        gc.save();
//        gc.translate(x, y); // 중심점을 기준으로 회전
//        gc.rotate(rotation);
//
//        double[] xPoints = new double[10];
//        double[] yPoints = new double[10];
//
//        for (int i = 0; i < 10; i++) {
//            double angle = Math.PI / 2 + i * Math.PI / 5;
//            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
//            xPoints[i] = radius * Math.cos(angle);
//            yPoints[i] = radius * Math.sin(angle);
//        }
//
//        gc.setFill(color);
//        gc.fillPolygon(xPoints, yPoints, 10);
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(1);
//        gc.strokePolygon(xPoints, yPoints, 10);
//
//        gc.restore();
//    }
//
//    // Movable 인터페이스 구현
//    @Override
//    public void move(double deltaTime) {
//        x += dx * deltaTime;
//        y += dy * deltaTime;
//        rotation += 100 * deltaTime; // 초당 100도 회전
//    }
//
//    @Override public double getDx() { return dx; }
//    @Override public double getDy() { return dy; }
//    @Override public void setDx(double dx) { this.dx = dx; }
//    @Override public void setDy(double dy) { this.dy = dy; }
//
//    // Collidable 인터페이스 구현
//    @Override
//    public Bounds getBounds() {
//        // 별의 중심점 (x, y)와 외곽 반지름(outerRadius)을 사용하여
//        // 별을 완벽하게 감싸는 사각형 경계를 계산합니다.
//        double minX = x - outerRadius;
//        double minY = y - outerRadius;
//        double width = outerRadius * 2;
//        double height = outerRadius * 2;
//
//        // RectangleBounds 객체를 생성하여 반환합니다.
//        return new RectangleBounds(minX, minY, width, height);
//    }
//
//    @Override
//    public boolean isColliding(Boundable other) {
//        return getBounds().intersects(other.getBounds());
//    }
//
//    @Override
//    public void handleCollision(Collidable other) {
//        // 충돌 로직 구현은 다른 객체와 동일하게 수행합니다.
//        if (collisionAction == CollisionAction.BOUNCE) {
//            if (other instanceof Movable) {
//                Movable movableOther = (Movable) other;
//                movableOther.setDx(-movableOther.getDx());
//                movableOther.setDy(-movableOther.getDy());
//                this.setDx(-this.getDx());
//                this.setDy(-this.getDy());
//            }
//        }
//    }
//
//    @Override public CollisionAction getCollisionAction() { return collisionAction; }
//}