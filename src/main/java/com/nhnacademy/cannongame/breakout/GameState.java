package com.nhnacademy.cannongame.breakout;

public enum GameState {
    // 게임이 시작되고 플레이 중인 상태
    PLAYING,

    // 게임이 일시 정지된 상태
    PAUSED,

    // 게임이 종료되어 승리한 상태
    WON,

    // 게임이 종료되어 패배한 상태
    GAME_OVER,

    RUNNING, // 게임이 아직 시작되지 않고 메인 메뉴에 있는 상태
    MENU
}