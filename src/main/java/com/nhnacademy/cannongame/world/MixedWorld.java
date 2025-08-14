package com.nhnacademy.cannongame.world;

import com.nhnacademy.cannongame.ball.Ball;
import com.nhnacademy.cannongame.ball.MovableBall;
import com.nhnacademy.cannongame.ball.PaintableBall;
import com.nhnacademy.cannongame.box.Box;
import com.nhnacademy.cannongame.box.MovableBox;
import com.nhnacademy.cannongame.box.PaintableBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MixedWorld {
    List<Ball> balls;
    List<Box> boxes;
    private double width;
    private double height;

    public MixedWorld(double width, double height) {
        this.width = width;
        this.height = height;
        this.balls = new ArrayList<>();
        this.boxes = new ArrayList<>();
    }

    public void addBall(Ball ball){
        balls.add(ball);
    }
    public void addBox(Box box){
        boxes.add(box);
    }
    public void update(double deltaTime){
        for (Ball ball : balls) {
            // instanceOf를 사용하여 MovableBall인지 확인
            if(ball instanceof MovableBall){
                ((MovableBall) ball).move(deltaTime);
            }
        }
        for (Box box : boxes) {
            if(box instanceof MovableBox){
                ((MovableBox) box).move(deltaTime);
            }
        }
    }
    public void render(GraphicsContext gc) {
        // 배경 그리기
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, width, height);

        // Ball을 위한 루프
        for (Ball ball : balls) {
            // instanceof를 사용하여 PaintableBall인지 확인
            if (ball instanceof PaintableBall) {
                ((PaintableBall) ball).draw(gc);
            }
        }

        // Box를 위한 루프
        for (Box box : boxes) {
            // instanceof를 사용하여 PaintableBox인지 확인
            if (box instanceof PaintableBox) {
                // PaintableBox에 대한 그리기 로직 (예시)
                PaintableBox paintableBox = (PaintableBox) box;
                gc.setFill(paintableBox.getColor());
                gc.fillRect(paintableBox.getX(), paintableBox.getY(), paintableBox.getWidth(), paintableBox.getHeight());
            }
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    public int getBallCount(){
        return balls.size();
    }
    public int getBoxCount(){
        return boxes.size();
    }
}
