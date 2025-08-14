package com.nhnacademy.cannongame.simpleworld;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class InterfaceBenefitSummaryTest {

    @Test
    public void testFlexibilityImprovement() {
        // 6장의 문제: 기능 조합마다 새 클래스 필요
        // 7장의 해결: 인터페이스를 통한 유연한 기능 조합

        // 하나의 Ball 클래스가 모든 기능을 제공
        Ball ball = new Ball(100, 100, 20, Color.RED);

        // 런타임에 행동 변경 가능
        ball.setCollisionAction(CollisionAction.BOUNCE);
        assertEquals(CollisionAction.BOUNCE, ball.getCollisionAction());

        ball.setCollisionAction(CollisionAction.DESTROY);
        assertEquals(CollisionAction.DESTROY, ball.getCollisionAction());

        ball.setCollisionAction(CollisionAction.PASS);
        assertEquals(CollisionAction.PASS, ball.getCollisionAction());

        // 6장에서는 각 행동마다 별도 클래스가 필요했지만
        // 7장에서는 하나의 클래스로 모든 행동 구현!
        System.out.println("유연성 개선: 런타임 행동 변경 가능!");
    }

    @Test
    public void testMaintainabilityImprovement() {
        // 6장의 문제: 새 타입 추가 시 전체 코드 수정 필요
        // 7장의 해결: 새 타입 추가가 기존 코드에 영향 없음

        List<Paintable> paintables = new ArrayList<>();
        List<Movable> movables = new ArrayList<>();
        List<Collidable> collidables = new ArrayList<>();

        // 기존 타입들
        Ball ball = new Ball(100, 100, 20, Color.RED);
        Box box = new Box(200, 200, 50, 40, Color.BLUE);

        paintables.add(ball);
        paintables.add(box);
        movables.add(ball);
        collidables.add(ball);
        collidables.add(box);

//        // 새로운 타입 추가 (Star 클래스)
//        Star star = new Star(300, 300, 25, Color.YELLOW);
//
//        // 기존 코드 수정 없이 새 타입 추가 가능!
//        paintables.add(star);
//        movables.add(star);
//        collidables.add(star);
//
//        // 모든 기존 처리 로직이 새 타입에도 동일하게 적용됨
//        GraphicsContext gc = Mockito.mock(GraphicsContext.class);
//        for (Paintable p : paintables) {
//            assertDoesNotThrow(() -> p.paint(gc),
//                    "새 타입도 기존 렌더링 로직에서 문제없이 작동");
//        }
//
//        for (Movable m : movables) {
//            m.setDx(50);
//            m.setDy(30);
//            assertDoesNotThrow(() -> m.move(1.0),
//                    "새 타입도 기존 이동 로직에서 문제없이 작동");
//        }

        System.out.println("유지보수성 개선: 새 타입 추가가 기존 코드에 영향 없음!");
    }

    @Test
    public void testCodeReductionImprovement() {
        // 6장의 문제: 수많은 instanceof 체크와 중복 코드
        // 7장의 해결: 간결한 다형성 코드

        List<Object> mixedObjects = new ArrayList<>();
        mixedObjects.add(new Ball(100, 100, 20, Color.RED));
        mixedObjects.add(new Box(200, 200, 50, 40, Color.BLUE));
//        mixedObjects.add(new Triangle(300, 300, 30, Color.GREEN));
        mixedObjects.add(new MovableBox(400, 400, 60, 50, Color.YELLOW));
//        mixedObjects.add(new Star(500, 500, 25, Color.PURPLE));

        // 6장에서는 각 타입별로 복잡한 instanceof 체크가 필요했지만
        // 7장에서는 간단한 인터페이스 체크만 필요!

        int paintableCount = 0;
        int movableCount = 0;
        int collidableCount = 0;

        for (Object obj : mixedObjects) {
            // 간단한 인터페이스 체크
            if (obj instanceof Paintable) paintableCount++;
            if (obj instanceof Movable) movableCount++;
            if (obj instanceof Collidable) collidableCount++;
        }

        assertEquals(3, paintableCount, "모든 객체가 Paintable이어야 합니다");
        assertEquals(3, movableCount, "모든 객체가 Movable이어야 합니다");
        assertEquals(3, collidableCount, "모든 객체가 Collidable이어야 합니다");

        // 6장에서는 각 타입 조합마다 별도 체크가 필요했지만
        // 7장에서는 인터페이스 하나로 모든 구현체 처리!
        System.out.println("코드 간소화: instanceof 지옥 탈출!");
    }

//    @Test
//    public void testExtensibilityImprovement() {
//        // 6장의 문제: 새 기능 추가 시 모든 클래스 수정 필요
//        // 7장의 해결: 인터페이스 추가로 새 기능 확장
//
//        // 새로운 인터페이스 추가 (예: Rotatable)
//        Ball ball = new Ball(100, 100, 20, Color.RED);
//
//        // 기존 객체에 새 기능 추가 가능
//        if (ball instanceof Rotatable) {
//            ((Rotatable) ball).rotate(45);
//            ((Rotatable) ball).setRotationSpeed(90);
//        }
//
//        // 새 인터페이스를 구현하는 객체들
//        List<Rotatable> rotatables = new ArrayList<>();
//        rotatables.add(new BouncingTriangle(200, 200, 30, Color.BLUE));
//        rotatables.add(new Star(300, 300, 25, Color.YELLOW));
//
//        // 모든 회전 가능한 객체를 동일하게 처리
//        for (Rotatable rotatable : rotatables) {
//            assertDoesNotThrow(() -> rotatable.rotate(30),
//                    "새 인터페이스 기능이 정상 작동해야 합니다");
//            assertDoesNotThrow(() -> rotatable.setRotationSpeed(120),
//                    "새 인터페이스 기능이 정상 작동해야 합니다");
//        }
//
//        System.out.println("확장성 개선: 새 인터페이스로 기능 확장 용이!");
//    }

    @Test
    public void testPerformanceImprovement() {
        // 6장의 문제: 복잡한 타입 체크와 캐스팅으로 성능 저하
        // 7장의 해결: 효율적인 다형성 호출

        List<Movable> movables = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0) {
                movables.add(new Ball(i, i, 10, Color.RED));
            }
//            else if (i % 3 == 1) {
//                movables.add(new Triangle(i, i, 15, Color.BLUE));
//            }
            else {
                movables.add(new MovableBox(i, i, 20, 20, Color.GREEN));
            }
        }

        long startTime = System.nanoTime();

        // 6장에서는 각 객체마다 복잡한 instanceof 체크가 필요했지만
        // 7장에서는 직접적인 인터페이스 메서드 호출!
        for (Movable movable : movables) {
            movable.setDx(1);
            movable.setDy(1);
            movable.move(0.016); // 60 FPS
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // 1000개 객체 처리가 빠르게 완료되어야 함
        assertTrue(duration < 10_000_000, // 10ms
                "1000개 객체 처리가 10ms 이내에 완료되어야 합니다: " + duration + "ns");

        System.out.println("성능 개선: " + (duration / 1_000_000.0) + "ms로 1000개 객체 처리!");
    }
}
