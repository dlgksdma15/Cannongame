package com.nhnacademy.cannongame.bounds;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.ball.Ball;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BallFactory {
    // Factory Method 패턴
    public abstract Ball createBall(Point center, double radius);
    public abstract Ball createBall(double x, double y, double radius);

    // Template Method 패턴: 공 여러 개를 만드는 알고리즘의 뼈대를 정의
    public List<Ball> createRandomBalls(int count, Bounds area){
        if(count < 0){
            throw new IllegalArgumentException("공의 개수는 0보다 작을 수 없습니다.");
        }

        List<Ball> balls = new ArrayList<>();
        Random random = new Random();

        double minX = area.getMinX();
        double minY = area.getMinY();
        double width = area.getMaxX() - minX;
        double height = area.getMaxY() - minY;

        for(int i = 0; i < count; i++){
            double randomRadius = 10 + random.nextDouble() * 30; // 10 ~ 40 사이의 랜덤 크기

            // 공의 중심이 영역 안에 완전히 포함되도록 위치 조정
            double x = minX + random.nextDouble() * (width - 2 * randomRadius) + randomRadius;
            double y = minY + random.nextDouble() * (height - 2 * randomRadius) + randomRadius;

            balls.add(createBall(x,y,randomRadius));
        }
        return balls;


    }
}
