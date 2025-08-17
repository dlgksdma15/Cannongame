package com.nhnacademy.cannongame.breakout;

import java.util.Optional;

public enum PowerUpType {
    WIDER_PADDLE("W", Optional.of(10.0)),
    MULTI_BALL("M", Optional.empty()),
    EXTRA_LIFE("+1", Optional.empty()),
    LASER("L", Optional.of(10.0)),
    SLOW_BALL("S", Optional.of(15.0)),
    STICKY_PADDLE("G", Optional.of(10.0));

    private final String symbol;
    private final Optional<Double> duration;

    PowerUpType(String symbol, Optional<Double> duration) {
        this.symbol = symbol;
        this.duration = duration;
    }

    public String getSymbol() {
        return symbol;
    }

    public Optional<Double> getDuration() {
        return duration;
    }
}