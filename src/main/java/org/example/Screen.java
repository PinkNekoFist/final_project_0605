package org.example;

public class Screen {
    public Point[] points; // central row of screen
    public Screen (double screenWidth, double screenHeight, Point[] points, Camera camera) {
        for (int i = 0;i < points.length;i++) {
            points[i] = new Point(camera.distanceFromScreen, (screenWidth / points.length) * (double)(i + 1) - (screenWidth / 2));
        }
        this.points = points;
    }
}
