package com.nhnacademy.cannongame.entity;

public abstract class Vector{
    public abstract double get(int index);
    public abstract void set(int index,double value);
    public abstract Vector createNew();
    public abstract int getDimensions(); // 차원 반환

    // 벡터 크기 계산
    public double magnitude(){
        double sum = 0;
        int dimensions = getDimensions();
        for(int i = 0; i < dimensions; i++){
            double value = get(i);
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    // 벡터를 정규화하여 크기를 1로 만듭니다.
    public Vector normalize() {
        double mag = magnitude();
        if (mag == 0.0) {
            return createNew();
        }

        Vector normalizedVector = createNew();
        int dimensions = getDimensions();
        for (int i = 0; i < dimensions; i++) {
            normalizedVector.set(i, get(i) / mag);
        }
        return normalizedVector;
    }

    public double dot(Vector other){
        if(getDimensions() != other.getDimensions()){
            throw new IllegalArgumentException("내적을 계산하려면 두 벡터의 차원이 같아야 합니다.");
        }
        double sum = 0.0;
        int dimensions = getDimensions();
        for(int i = 0; i < dimensions; i++){
            sum += get(i) * other.get(i);
        }
        return sum;
    }
    //public abstract Vector multiply(double scalar);
}
