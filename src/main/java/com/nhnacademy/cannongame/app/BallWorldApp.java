package com.nhnacademy.cannongame.app;

import com.nhnacademy.cannongame.ball.PaintableBall;
import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.world.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class BallWorldApp extends Application {
    protected static final int WORLD_WIDTH = 800;
    protected static final int WORLD_HEIGHT = 600;
    protected final Random random = new Random();

    protected World world;
    protected Canvas canvas;
    protected GraphicsContext gc;



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        world = new World(WORLD_WIDTH,WORLD_HEIGHT);
        Canvas canvas = new Canvas(WORLD_WIDTH,WORLD_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        // 랜덤한 공 5개 생성
        createRandomBalls(5);
        
        // 마우스 클릭 이벤트 처리 설정
        canvas.setOnMouseClicked(this::handleMouseClick);

        world.draw(gc);
        
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root,WORLD_WIDTH,WORLD_HEIGHT);
        stage.setTitle("Ball World");
        stage.setScene(scene);
        //stage.setResizable(false); // 창 크기 변경 불가

        stage.show();

        draw();
    }

    private void handleMouseClick(MouseEvent event) {
        try{
            double x = event.getX();
            double y = event.getY();
            double radius = 20; // 고정된 반지름

            // 새 공 생성 및 추가
            PaintableBall newBall = new PaintableBall(new Point(x,y),radius,Color.RED);
            world.add(newBall);

            // 화면 다시 그리기
            draw();
        } catch (IllegalStateException e ){
            System.out.println("경계 근처에는 공을 추가할 수 없습니다: " + e.getMessage());
        }
    }
    private void draw(){
        world.draw(gc);
    }

    private void createRandomBalls(int n) {
        for (int i = 0; i < n; i++) {
            try {
                double radius = random.nextDouble() * 20 + 10; // 10 ~ 30 사이의 반지름
                double x = random.nextDouble() * (WORLD_WIDTH - (2 * radius)) + radius;
                double y = random.nextDouble() * (WORLD_HEIGHT - (2 * radius)) + radius;
                Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                // 공 생성 및 추가
                PaintableBall ball = new PaintableBall(new Point(x, y), radius, color);
                world.add(ball);
            } catch (IllegalStateException e) {
                // 경계를 벗어나는 공이 생성되면 예외를 잡고 재시도
                System.err.println("랜덤 볼 생성 재시도: " + e.getMessage());
                i--;
            }
        }
    }
}
