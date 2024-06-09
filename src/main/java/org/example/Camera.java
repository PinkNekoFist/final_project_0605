package org.example;

interface Movement {
    public void move (double distance);
}

interface Rotation {
    public void rotate (int angle);
}

public class Camera implements Movement, Rotation{
    Point position;
    double fov;
    int angle;
    double distanceFromScreen;
    double renderDistance;

    public Camera(double x, double y, int angle, double fov, int terminalWidth, double renderDistance) {
        position = new Point(x, y);
        this.fov = fov; // 70
        this.distanceFromScreen = (((double)terminalWidth * 0.005 / 2) / Math.tan(Math.toRadians(fov / 2))); // about 0.7426
        this.angle = angle;
        this.renderDistance = renderDistance;
        System.out.println("x: " + x + " y: " + y + " angle: " + angle + " fov: " + fov + " distanceFromScreen: " + distanceFromScreen);
    }

    public void move(double distance) {
        position.x += distance * Math.cos(Math.toRadians(angle));
        position.y += distance * Math.sin(Math.toRadians(angle));
    }

    public void rotate(int angle) {
        this.angle += angle;
        if (this.angle == 0) this.angle = 1;
    }
}

