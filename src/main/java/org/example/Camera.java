package org.example;

public class Camera {
    double x;
    double y;
    double angle;

    public Camera(double x, double y, double angle) {

    }

    public void move(double distance) {
        x += 0;
        y += 0;
    }

    public void rotate(double angle) {
        this.angle += angle;
    }
}

