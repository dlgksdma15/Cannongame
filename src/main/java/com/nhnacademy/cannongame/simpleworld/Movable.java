package com.nhnacademy.cannongame.simpleworld;

public interface Movable {
    void move(double deltaTime); // 시간 기반 이동
    double getDx(); // 속도 조회
    double getDy(); // 속도 조회
    void setDx(double dx); // 속도 설정
    void setDy(double dy); // 속도 설정
}
