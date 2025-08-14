package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import javafx.scene.paint.Color;

/**
 * MovableBox의 움직임 기능에 색상 기능을 추가한 클래스.
 * 상속을 통해 기능을 조합하는 방식을 보여줍니다.
 */
public class PaintableMovableBox extends MovableBox {
    private Color color;

    /**
     * PaintableMovableBox의 생성자.
     * Box의 위치와 크기를 초기화하고, 속도와 색상을 설정합니다.
     *
     * @param position 박스의 왼쪽 상단 모서리 좌표
     * @param width    박스의 너비
     * @param height   박스의 높이
     * @param color    박스의 색상
     */
    public PaintableMovableBox(Point position, double width, double height, Color color) {
        super(position, width, height); // MovableBox의 생성자 호출
        if (color == null) {
            throw new IllegalArgumentException("색상은 null일 수 없습니다.");
        }
        this.color = color;
    }

    /**
     * 색상 없이 초기화할 경우, 기본 색상(빨강)을 설정하는 생성자.
     *
     * @param position 박스의 왼쪽 상단 모서리 좌표
     * @param width    박스의 너비
     * @param height   박스의 높이
     */
    public PaintableMovableBox(Point position, double width, double height) {
        this(position, width, height, Color.RED);
    }

    // 테스트 코드에 필요한, int 매개변수를 받는 생성자 추가
    public PaintableMovableBox(int x, int y, int width, int height, Color color) {
        this(new Point(x, y), width, height, color);
    }
    /**
     * 박스의 현재 색상을 반환합니다.
     *
     * @return 현재 색상 객체
     */
    public Color getColor() {
        return color;
    }

    /**
     * 박스의 색상을 설정합니다.
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
