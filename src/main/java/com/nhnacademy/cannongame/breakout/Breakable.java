package com.nhnacademy.cannongame.breakout;

public interface Breakable {
    // 파괴 시 얻는 점수를 반환하는 추상 메소드
    int getPoints();

    void hit(); // 벽돌이 타격을 받았을 때 호출되는 메소드
    boolean isDestroyed(); // 벽돌이 파괴되었는지 여부를 반환하는 메소드
}
