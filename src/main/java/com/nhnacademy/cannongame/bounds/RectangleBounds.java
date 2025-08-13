package com.nhnacademy.cannongame.bounds;

public class RectangleBounds extends Bounds{
    private double minX;
    private double minY;
    private double width;
    private double height;

    public RectangleBounds(double minX, double minY, double width, double height) {
        if(width < 0 || height < 0){
            throw new IllegalArgumentException("가로와 세로는 0이상만 올 수 있습니다.");
        }
        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
    }

    /**
     *  구현 요구사항:
     * - 생성자에서 크기 유효성 검사
     * - 4개의 추상 메서드 모두 구현
     */

    @Override
    public double getMinX(){
        return minX;
    }

    @Override
    public double getMaxX() {
        return minX + width;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxY() {
        return minY + height;
    }

}
