package com.nhnacademy.cannongame.shapes;

import com.nhnacademy.cannongame.Point;

public class Rectangle extends Shape {
    private Point position;
    private double width;
    private double height;


    public Rectangle(Point position, double width, double height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Rectangle(double x, double y, double width, double height){
        this.position = new Point(x,y);
        this.width = width;
        this.height = height;

    }
    @Override
    public double getArea(){
        return width * height;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public String getShapeType() {
        return "Rectangle";
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getPerimeter() {
        return (width + height) * 2;
    }
}
