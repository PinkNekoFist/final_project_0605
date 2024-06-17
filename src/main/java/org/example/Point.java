package org.example;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void normalize() {
        double len = Math.sqrt(x * x + y * y);
        x = x / len;
        y = y / len;
    }
}
