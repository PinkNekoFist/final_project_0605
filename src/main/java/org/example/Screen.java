package org.example;

public class Screen {
    double screenWidth;
    double screenHeight;
    Point[] points; // central row of screen
    Screen(double screenWidth, double screenHeight, Point[] points, Camera camera) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        for (int i = 0;i < points.length;i++) {
            points[i].x = camera.distanceFromScreen;
            points[i].y = (screenWidth / points.length) * (i + 1) - (screenWidth / 2);
        }
        this.points = points;
    }
}
