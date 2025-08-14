package com.nhnacademy.cannongame.simpleworld;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleWorldTest {

    private SimpleWorld world;

    @BeforeEach
    public void setUp() {
        world = new SimpleWorld(800, 600);
    }

    @Test
    public void testWorldCreation() {
        assertEquals(800, world.getWidth(), "World 너비가 올바르게 설정되지 않았습니다");
        assertEquals(600, world.getHeight(), "World 높이가 올바르게 설정되지 않았습니다");
        assertEquals(0, world.getObjectCount(), "초기 객체 수는 0이어야 합니다");
    }

    @Test
    public void testBoundaryCreation() {
        world.createBoundaries();

        // 경계가 생성되었는지 확인
        List<Box> boundaries = world.getBoundaries();
        assertEquals(4, boundaries.size(), "4개의 경계가 생성되어야 합니다");

        // 모든 경계가 BOUNCE 액션을 가지는지 확인
        for (Box boundary : boundaries) {
            assertEquals(CollisionAction.BOUNCE, boundary.getCollisionAction(),
                    "경계는 BOUNCE 액션을 가져야 합니다");
        }
    }

    @Test
    public void testMixedObjectManagement() {
        // 6장의 문제: Ball과 Box를 별도 리스트로 관리
        // 7장의 해결: 모든 객체를 하나의 리스트로 관리

        Ball ball = new Ball(100, 100, 20, Color.RED);
        Box box = new Box(200, 200, 50, 40, Color.BLUE);
//        Triangle triangle = new Triangle(300, 300, 30, Color.GREEN);
        MovableBox movableBox = new MovableBox(400, 400, 60, 50, Color.YELLOW);

        world.addObject(ball);
        world.addObject(box);
//        world.addObject(triangle);
        world.addObject(movableBox);

        assertEquals(3, world.getObjectCount(), "모든 타입의 객체가 추가되어야 합니다");

        // 모든 객체가 하나의 컬렉션에서 관리됨
        List<Object> objects = world.getGameObjects();
        assertEquals(3, objects.size(), "모든 객체가 하나의 리스트에서 관리됩니다");
    }

    @Test
    public void testPolymorphicUpdate() {
        Ball ball = new Ball(100, 100, 20, Color.RED);
        ball.setDx(50);
        ball.setDy(30);

        MovableBox movableBox = new MovableBox(200, 200, 40, 30, Color.BLUE);
        movableBox.setDx(-25);
        movableBox.setDy(35);

//        Triangle triangle = new Triangle(300, 300, 25, Color.GREEN);
//        triangle.setDx(40);
//        triangle.setDy(-20);

        Box staticBox = new Box(400, 400, 50, 50, Color.GRAY); // 움직이지 않음

        world.addObject(ball);
        world.addObject(movableBox);
//        world.addObject(triangle);
        world.addObject(staticBox);

        // 위치 저장
        double ballX = ball.getX();
        double ballY = ball.getY();
        double boxX = movableBox.getX();
        double boxY = movableBox.getY();
//        double triangleX = triangle.getX();
//        double triangleY = triangle.getY();
        double staticBoxX = staticBox.getX();
        double staticBoxY = staticBox.getY();

        // 업데이트 수행
        world.update(1.0);

        // Movable 객체들만 이동했는지 확인
        assertNotEquals(ballX, ball.getX(), "Ball이 이동하지 않았습니다");
        assertNotEquals(ballY, ball.getY(), "Ball이 이동하지 않았습니다");
        assertNotEquals(boxX, movableBox.getX(), "MovableBox가 이동하지 않았습니다");
        assertNotEquals(boxY, movableBox.getY(), "MovableBox가 이동하지 않았습니다");
//        assertNotEquals(triangleX, triangle.getX(), "Triangle이 이동하지 않았습니다");
//        assertNotEquals(triangleY, triangle.getY(), "Triangle이 이동하지 않았습니다");

        // 정적 Box는 이동하지 않음
        assertEquals(staticBoxX, staticBox.getX(), 0.001, "정적 Box는 이동하면 안됩니다");
        assertEquals(staticBoxY, staticBox.getY(), 0.001, "정적 Box는 이동하면 안됩니다");
    }

    @Test
    public void testPolymorphicRender() {
        Ball ball = new Ball(100, 100, 20, Color.RED);
        Box box = new Box(200, 200, 50, 40, Color.BLUE);
//        Triangle triangle = new Triangle(300, 300, 30, Color.GREEN);

        world.addObject(ball);
        world.addObject(box);
//        world.addObject(triangle);

        GraphicsContext gc = Mockito.mock(GraphicsContext.class);

        // 모든 객체가 동일한 방식으로 렌더링됨
        assertDoesNotThrow(() -> world.render(gc), "World 렌더링 중 예외 발생");

        // 6장에서는 각 타입별로 별도 렌더링 로직이 필요했지만
        // 7장에서는 Paintable 인터페이스로 통합 처리!
    }

    @Test
    public void testCollisionDetectionAndHandling() {
        Ball ball1 = new Ball(100, 100, 20, Color.RED);
        ball1.setDx(50);
        ball1.setCollisionAction(CollisionAction.BOUNCE);

        Ball ball2 = new Ball(130, 100, 15, Color.BLUE);
        ball2.setDx(-30);
        ball2.setCollisionAction(CollisionAction.BOUNCE);

        Box obstacle = new Box(200, 200, 40, 40, Color.GRAY);
        obstacle.setCollisionAction(CollisionAction.BOUNCE);

        world.addObject(ball1);
        world.addObject(ball2);
        world.addObject(obstacle);

        // 충돌 전 속도 저장
        double ball1DxBefore = ball1.getDx();
        double ball2DxBefore = ball2.getDx();

        // 충돌이 발생할 수 있도록 위치 조정
        assertTrue(ball1.isColliding(ball2), "두 Ball이 충돌해야 합니다");

        // 업데이트로 충돌 처리
        world.update(0.1);

        // 충돌 후 속도 변경 확인
        assertNotEquals(ball1DxBefore, ball1.getDx(), "Ball1의 속도가 변경되어야 합니다");
        assertNotEquals(ball2DxBefore, ball2.getDx(), "Ball2의 속도가 변경되어야 합니다");
    }
}
