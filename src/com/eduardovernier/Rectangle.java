package com.eduardovernier;

public class Rectangle {

    double x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getAspectRatio() {
        return Math.min(width/height, height/width);
    }

    public double getArea() {
        return width * height;
    }
}
