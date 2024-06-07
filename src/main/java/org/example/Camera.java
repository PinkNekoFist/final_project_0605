package org.example;

interface Movement {
    public void move (double distance);
}

interface Rotation {
    public void rotate (int angle);
}

public class Camera implements Movement, Rotation{
    double x;
    double y;
    double fov;
    int angle;
    double distanceFromScreen;
    double renderDistance;

    public Camera(double x, double y, int angle, double fov, int terminalWidth, double renderDistance) {
        this.x = x;
        this.y = y;
        this.fov = fov; // 70
        this.distanceFromScreen = (((double)terminalWidth * 0.005 / 2) / Math.tan(Math.toRadians(fov / 2))); // about 0.7426
        this.angle = angle;
        this.renderDistance = renderDistance;
        System.out.println("x: " + x + " y: " + y + " angle: " + angle + " fov: " + fov + " distanceFromScreen: " + distanceFromScreen);
    }

    public void move(double distance) {
        x += 0;
        y += 0;
    }

    public void rotate(int angle) {
        this.angle += angle;
    }
}

