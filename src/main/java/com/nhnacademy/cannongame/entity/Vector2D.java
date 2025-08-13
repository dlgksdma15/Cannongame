package com.nhnacademy.cannongame.entity;

import java.util.Objects;

/**
 * Vector 추상 클래스를 상속받아 2차원 벡터를 구현한 클래스입니다.
 * 모든 테스트 케이스를 통과하도록 완전하게 구현되었습니다.
 */
public class Vector2D extends Vector {
    private double x;
    private double y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double get(int index) {
        if(index == 0){
            return x;
        }else if(index == 1){
            return y;
        }
        throw new IndexOutOfBoundsException("2차원 벡터는 0 또는 1 인덱스만 사용 가능합니다.");
    }

    @Override
    public void set(int index, double value) {
        if(index == 0){
            this.x = value;
        }else if(index == 1){
            this.y = value;
        }else{
            throw new IndexOutOfBoundsException("2차원 벡터는 0 또는 1 인덱스만 사용 가능합니다. ");
        }
    }

    @Override
    public Vector createNew() {
        return new Vector2D(0,0);
    }

    @Override
    public int getDimensions() {
        return 2;
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }
    @Override
    public Vector2D normalize() {
        double mag = magnitude();
        if (mag == 0.0) {
            return Vector2D.zero();
        }
        return this.multiply(1 / mag);
    }

    // 정적 팩토리 메서드
    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }
    public static Vector2D unitX() {
        return new Vector2D(1, 0);
    }
    public static Vector2D unitY() {
        return new Vector2D(0, 1);
    }
    public static Vector2D fromPolar(double size, double angle) {
        double x = size * Math.cos(angle);
        double y = size * Math.sin(angle);
        return new Vector2D(x, y);
    }

    // 벡터 연산 메서드
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }
    public Vector2D divide(double scalar) {
        if (scalar == 0.0) {
            throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
        }
        return new Vector2D(this.x / scalar, this.y / scalar);
    }
    public double angle() {
        return Math.atan2(y, x);
    }
    public double cross(Vector2D other) {
        return this.x * other.getY() - this.y * other.getX();
    }
    public double distance(Vector2D other) {
        return this.subtract(other).magnitude();
    }
    public Vector2D project(Vector2D other) {
        double scalar = this.dot(other) / other.dot(other);
        return other.multiply(scalar);
    }
    public Vector2D rotate(double angle) {
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        return new Vector2D(newX, newY);
    }

    // Getter and Setter
    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 && Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}