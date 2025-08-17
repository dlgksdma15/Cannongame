package com.nhnacademy.cannongame.breakout;

import com.nhnacademy.cannongame.simpleworld.Collidable;
import com.nhnacademy.cannongame.simpleworld.CollisionAction;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Iterator;

public class ExplodingBrick extends SimpleBrick implements Exploding {

    // 생성자를 BrickType을 받도록 수정
    public ExplodingBrick(double x, double y, double width, double height, BrickType type) {
        // 부모 클래스인 SimpleBrick의 생성자를 호출.
        // SimpleBrick이 BrickType을 받도록 설계되어야 함.
        super(x, y, width, height, type);
    }


    @Override
    public void hit() {
        super.hit(); // SimpleBrick의 hit()을 호출하여 파괴 상태로 변경
    }

    @Override
    public void triggerExplosion(List<Breakable> allBricks) {
        Iterator<Breakable> iterator = allBricks.iterator();
        while (iterator.hasNext()) {
            Breakable brick = iterator.next();

            // Breakable이 Collidable 인터페이스를 구현했는지 확인
            if (brick instanceof Collidable) {
                Collidable collidableBrick = (Collidable) brick;

                // 파괴된 벽돌(자기 자신)은 건너뜁니다.
                if (this.equals(brick) || brick.isDestroyed()) {
                    continue;
                }

                // 폭발 반경(예: 100px) 내의 벽돌을 찾아 hit() 호출
                double distance = Math.sqrt(
                        Math.pow(collidableBrick.getBounds().getCenterX() - this.getBounds().getCenterX(), 2) +
                                Math.pow(collidableBrick.getBounds().getCenterY() - this.getBounds().getCenterY(), 2)
                );

                if (distance < 100) { // 폭발 반경
                    brick.hit(); // 주변 벽돌에 피해 적용
                }
            }
        }
    }

}