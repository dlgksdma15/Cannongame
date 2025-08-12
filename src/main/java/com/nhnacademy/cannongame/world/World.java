package com.nhnacademy.cannongame.world;

import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.PaintableBall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class World {

    private final double width; // 월드 너비
    private final double height; // 월드 높이
    private final ArrayList<Ball> balls = new ArrayList<>();

    public World(double width, double height) {
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("너비와 높이는 0이하로 올 수 없습니다.");
        }
        this.width = width;
        this.height = height;
    }

    public void add(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("추가하려는 볼은 null일 수 없습니다.");
        }
        // 경계 체크: 공의 모든 부분이 월드 안에 있어야 함
        // 공의 중심 x좌표 - 반지름 >= 0 (왼쪽 경계)
        // 공의 중심 x좌표 + 반지름 <= 월드 너비 (오른쪽 경계)
        // 공의 중심 y좌표 - 반지름 >= 0 (위쪽 경계)
        // 공의 중심 y좌표 + 반지름 <= 월드 높이 (아래쪽 경계)

        // 이 네 가지 조건을 모두 만족해야 공이 월드 안에 있다고 판단할 수 있습니다.
        // 따라서, 만약 이 조건 중 하나라도 위반하면(밖으로 벗어나면) 예외를 발생시켜야 합니다.
        // '||'(논리합)을 사용하여 하나의 조건이라도 거짓(공이 밖으로 벗어남)이면
        // 예외를 던지도록 수정했습니다.
        if ((ball.getCenter().getX() - ball.getRadius() < 0) || // 왼쪽 경계를 넘어선 경우
                (ball.getCenter().getX() + ball.getRadius() > this.width) || // 오른쪽 경계를 넘어선 경우
                (ball.getCenter().getY() - ball.getRadius() < 0) || // 위쪽 경계를 넘어선 경우
                (ball.getCenter().getY() + ball.getRadius() > this.height)) { // 아래쪽 경계를 넘어선 경우
            throw new IllegalArgumentException("공이 월드 경계를 벗어납니다.");
        }
        this.balls.add(ball);
    }

    public void remove(Ball ball){
        if(balls.isEmpty()){
            throw new NoSuchElementException("삭제할 공이 없습니다.");
        }
        balls.remove(ball);
    }

    public void clear(){
        balls.clear();
    }

    public List<Ball> getBalls(){
        return new ArrayList<>(balls);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public void draw(GraphicsContext gc){
        // 배경을 흰색으로 지우기
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,this.width,this.height);

        // 모든 공을 순회하며 그리기
        // World에 저장된 볼 리스트가 PaintableBall 타입이어야 함
        for(Ball ball : balls){
            if(ball instanceof PaintableBall){
                ((PaintableBall) ball).draw(gc);
            }
        }
    }

    public double getBallCount() {
        return balls.size();
    }
}
