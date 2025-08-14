package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import javafx.scene.paint.Color;

public class PaintableBox extends Box {
    private Color color;

    // 생성자 1: 위치, 크기, 색상을 받아 초기화
    public PaintableBox(Point position, double width, double height, Color color) {
        super(position, width, height); // Box의 생성자 호출
        if (color == null) {
            throw new IllegalArgumentException("색상은 null이 올 수 없습니다.");
        }
        this.color = color;
    }

    // 생성자 2: 색상 없이 초기화 (기본값 설정)
    public PaintableBox(Point position, double width, double height) {
        this(position, width, height, Color.RED);
    }

    // getter 메서드
    public Color getColor() {
        return color;
    }

    // setter 메서드: 색상 설정
    public void setColor(Color color) {
        if (color == null) {
            color = Color.RED; // 요구사항에 따라 null일 경우 기본값 설정
            throw new IllegalArgumentException();

        }
        this.color = color;
    }

}