package org.example;

public class Camera {
    double x;
    double y;
    double angle;
    double fov;
    double distanceFromScreen;

    public Camera(double x, double y, double angle, double fov, int terminalWidth) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.fov = fov;
        this.distanceFromScreen = ((double)terminalWidth / (2 * Math.tan(Math.toRadians(angle))));
    }

    public void move(double distance) {
        x += 0;
        y += 0;
    }

    public void rotate(double angle) {
        this.angle += angle;
    }
}

