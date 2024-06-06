package org.example;

public class Camera {
    double x;
    double y;
    double fov;
    double angle;
    double distanceFromScreen;

    public Camera(double x, double y, double angle, double fov, int terminalWidth) {
        this.x = x;
        this.y = y;
        this.fov = fov; // 70
        this.distanceFromScreen = ((double)terminalWidth * 0.005 / Math.tan(Math.toRadians(fov / 2))); // about 0.735
        System.out.println("x: " + x + " y: " + y + " angle: " + " fov: " + fov + " distanceFromScreen: " + distanceFromScreen);
    }

    public void move(double distance) {
        x += 0;
        y += 0;
    }

    public void rotate(double angle) {
        this.angle += angle;
    }
}

