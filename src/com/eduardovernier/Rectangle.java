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

    public void reposition(Rectangle oldCanvas, Rectangle newCanvas) {
        this.x = ((this.x - oldCanvas.x) / oldCanvas.width) * newCanvas.width + newCanvas.x;
        this.y = ((this.y - oldCanvas.y) / oldCanvas.height) * newCanvas.height + newCanvas.y;
        this.width = (newCanvas.width / oldCanvas.width) * this.width;
        this.height = (newCanvas.height / oldCanvas.height) * this.height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
