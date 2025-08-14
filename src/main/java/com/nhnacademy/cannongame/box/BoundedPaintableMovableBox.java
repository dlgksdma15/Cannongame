package com.nhnacademy.cannongame.box;

import com.nhnacademy.cannongame.Point;
import com.nhnacademy.cannongame.bounds.RectangleBounds;
import javafx.scene.paint.Color;

public class BoundedPaintableMovableBox extends PaintableMovableBox{
    private Color color;

    public BoundedPaintableMovableBox(Point position, double width, double height, Color color) {
        super(position, width, height, color);
    }

    public BoundedPaintableMovableBox(Point position, double width, double height) {
        super(position, width, height);
    }

    public BoundedPaintableMovableBox(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }
    /**
     * Box의 경계를 나타내는 RectangleBounds 객체를 반환합니다.
     * 상속받은 Box 클래스의 메서드를 재정의합니다.
     * @return RectangleBounds 객체
     */
    @Override
    public RectangleBounds getBounds() {
        return new RectangleBounds(getX(), getY(), getWidth(), getHeight());
    }
}
