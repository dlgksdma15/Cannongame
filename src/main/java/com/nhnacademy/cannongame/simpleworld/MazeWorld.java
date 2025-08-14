package com.nhnacademy.cannongame.simpleworld;

import javafx.scene.paint.Color;

public class MazeWorld extends SimpleWorld {

    private final int TILE_SIZE = 50; // 미로 타일 한 칸의 크기

    // 2차원 배열로 정의된 미로 구조
    private int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public MazeWorld(double width, double height) {
        super(width, height);
        // 미로를 게임 세계에 추가
        loadMaze();
    }

    private void loadMaze() {
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if (maze[y][x] == 1) { // 벽(Wall)인 경우
                    // 벽돌 객체를 생성하여 SimpleWorld에 추가
                    Box wall = new Box(
                            x * TILE_SIZE, y * TILE_SIZE,
                            TILE_SIZE, TILE_SIZE,
                            Color.BROWN,
                            CollisionAction.BOUNCE // 벽에 닿으면 튕기도록 설정
                    );
                    addObject(wall); // SimpleWorld의 add 메서드를 사용하여 객체 리스트에 추가
                } else if (maze[y][x] == 2) { // 출구(Exit)인 경우
                    // 출구 객체를 생성하여 추가 (색상과 충돌 액션 다르게 설정 가능)
                    Box exit = new Box(
                            x * TILE_SIZE, y * TILE_SIZE,
                            TILE_SIZE, TILE_SIZE,
                            Color.GREEN,
                            CollisionAction.PASS // 출구는 통과하도록 설정
                    );
                    addObject(exit);
                }
            }
        }
    }
}