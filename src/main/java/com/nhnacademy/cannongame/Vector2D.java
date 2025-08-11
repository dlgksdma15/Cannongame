package com.nhnacademy.cannongame;

// Vector는 변위(이동량)를 나타냅니다
// Point + Vector2D = Point: 위치에서 이동하여 새로운 위치
// Point - Point = Vector2D: 두 위치 간의 변위
// Vector2D + Vector2D = Vector2D: 변위의 합성

public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.getX(), this.y + other.getY());
    }
    public Vector2D subtract(Vector2D other){
        return new Vector2D(this.x - other.getX(), this.y - other.getY());
    }
    public Vector2D multiply(double scalar){
        return new Vector2D(this.x * scalar, this.y * scalar);
    }
    public double magnitude(){ // 크기(벡터의 길이)
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    public Vector2D normalize(){ // 정규화
        double mag = magnitude();
        if(mag == 0){
            return new Vector2D(0,0);
        }
        return this.multiply(1 / mag);
    }
    // 내적
    public double dot(Vector2D other){
        return this.x * other.getX() + this.y * other.getY();
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector2D{" + x + y + '}';
    }
}
