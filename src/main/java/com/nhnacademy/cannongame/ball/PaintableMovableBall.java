package com.nhnacademy.cannongame.ball;

import com.nhnacademy.cannongame.Point;
import javafx.scene.paint.Color;

/**
 * MovableBall의 움직임 기능에 색상 기능을 추가한 클래스.
 * 상속을 통해 기능을 조합하는 방식을 보여줍니다.
 */
public class PaintableMovableBall extends MovableBall {
    private Color color;

    /**
     * PaintableMovableBall의 생성자.
     * Ball의 위치와 반지름을 초기화하고, 속도와 색상을 설정합니다.
     *
     * @param center 공의 중심점 좌표
     * @param radius 공의 반지름
     * @param color  공의 색상
     */
    public PaintableMovableBall(Point center, double radius, Color color) {
        super(center, radius); // MovableBall의 생성자 호출
        if (color == null) {
            throw new IllegalArgumentException("색상은 null이 올 수 없습니다.");
        }
        this.color = color;
    }

    /**
     * 색상 없이 초기화할 경우, 기본 색상(빨강)을 설정하는 생성자.
     *
     * @param center 공의 중심점 좌표
     * @param radius 공의 반지름
     */
    public PaintableMovableBall(Point center, double radius) {
        this(center, radius, Color.RED);
    }

    /**
     * 공의 현재 색상을 반환합니다.
     *
     * @return 현재 색상 객체
     */
    public Color getColor() {
        return color;
    }

    /**
     * 공의 색상을 설정합니다.
     * null이 입력될 경우, 기본 색상(빨강)으로 설정됩니다.
     *
     * @param color 새로운 색상 객체
     */
    public void setColor(Color color) {
        if (color == null) {
            color = Color.RED;
        }
        this.color = color;
    }
}

