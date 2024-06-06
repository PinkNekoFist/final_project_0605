package org.example;

public class Screen {
    double screenWidth;
    double screenHeight;
    Point[] points; // central row of screen
    Screen(double screenWidth, double screenHeight, Point[] points, Camera camera) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        for (int i = 0;i < points.length;i++) {
            points[i] = new Point(camera.distanceFromScreen, (screenWidth / points.length) * (double)(i + 1) - (screenWidth / 2));
            // System.out.println(points[i].x + " " + points[i].y);
        }
        this.points = points;
    }
}
