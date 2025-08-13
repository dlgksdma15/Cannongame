package com.nhnacademy.cannongame.entity;

public class Vector2D extends Vector {
    private double x;
    private double y;

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
}
