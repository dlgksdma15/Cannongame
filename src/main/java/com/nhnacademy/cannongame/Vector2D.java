package com.nhnacademy.cannongame;

// Vector는 변위(이동량)를 나타냅니다
// Point + Vector2D = Point: 위치에서 이동하여 새로운 위치
// Point - Point = Vector2D: 두 위치 간의 변위
// Vector2D + Vector2D = Vector2D: 변위의 합성

import java.util.Objects;

public class Vector2D {
    private double x;
    private double y;
    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

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
//    public static Vector2D fromPolar(double size, double angle){
//        double x = size * Math.cos(angle);
//        double y = size * Math.sin(angle);
//        return new Vector2D(x, y);
//    }
//    // 영벡터
//    public static Vector2D zero(){
//        return new Vector2D(0,0);
//    }
//    // 단위 X벡터
//    public static Vector2D unitX(){
//        return new Vector2D(1,0);
//    }
//    // 단위 Y벡터
//    public static Vector2D unitY(){
//        return new Vector2D(0,1);
//    }
//    public Vector2D divide(double scalar){
//        return new Vector2D(x / scalar, y / scalar);
//    }
//    public double angle(){
//        return Math.atan2(y, x);
//    }
    // 내적
    public double dot(Vector2D other){
        return this.x * other.getX() + this.y * other.getY();
    }
//    /**
//     * 다른 벡터와 외적(cross product)을 계산합니다.
//     * 2D 벡터의 외적은 z축 성분을 반환합니다.
//     */
//    public double cross(Vector2D other) {
//        return this.x * other.getY() - this.y * other.getX();
//    }
//
//    /**
//     * 다른 벡터까지의 거리를 계산합니다.
//     */
//    public double distance(Vector2D other) {
//        return this.subtract(other).magnitude();
//    }
//
//    /**
//     * 이 벡터를 다른 벡터에 투영한 벡터를 계산합니다.
//     */
//    public Vector2D project(Vector2D other) {
//        double scalar = this.dot(other) / other.dot(other);
//        return other.multiply(scalar);
//    }
//
//    /**
//     * 벡터를 지정된 각도만큼 회전시킵니다.
//     */
//    public Vector2D rotate(double angle) {
//        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
//        double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
//        return new Vector2D(newX, newY);
//    }

    public double getX(){
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

//    @Override
//    public boolean equals(Object o) {
//        // 객체 자기 자신인지 확인
//        if (this == o) return true;
//        // null이거나 클래스 타입이 다르면 다른 객체
//        if (o == null || getClass() != o.getClass()) return false;
//
//        // 형변환
//        Vector2D vector2D = (Vector2D) o;
//
//        // x와 y 필드의 값이 같은지 비교
//        return Double.compare(vector2D.x, x) == 0 &&
//                Double.compare(vector2D.y, y) == 0;
//    }
//    @Override
//    public int hashCode() {
//        // x와 y 필드를 기반으로 해시코드 생성
//        return Objects.hash(x, y);
//    }
    @Override
    public String toString() {
        return "Vector2D{" + x + y + '}';
    }

}
