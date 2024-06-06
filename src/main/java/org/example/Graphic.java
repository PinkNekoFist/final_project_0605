package org.example;


public class Graphic {
    public char[][] frame;
    public int width, height;
    private Point[] pointsOnScreen;

    public Graphic(int width, int height, Camera camera, Screen screen) {
        this.width = width;
        this.height = height;

        // init points not good always make new object

        frame = new char[height][width];
        // test
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = ' ';
            }
        }

        // rayCasting
        rayCasting(width, height, frame, camera, screen);
    }

    private void rayCasting (int width, int height, char[][] frame, Camera camera, Screen screen) {
        // terminal per line has width (208 while full screen in my pc) char
        // one char is n = 0.005 in map
        // first step : get every column's position in screen on map, by rotate a line and move it
        Point[] pointsAfter = new Point[screen.points.length];
        for (int i = 0;i < width; i++) {
            pointsAfter[i] = rotatePoint(screen.points[i], camera.angle);
            pointsAfter[i].x += camera.x;
            pointsAfter[i].y += camera.y;
            // System.out.println(pointsAfter[i].x + " " + pointsAfter[i].y);
        }

        // second step : find a vector from camera to points

        // third step : according to vector find a wall on map

        // fourth step : return distance from wall to point
    }

    private Point rotatePoint (Point point, int angle) {
        // System.out.println(point.x + " " + point.y + " " + angle);
        Point newPoint = new Point(point.x, point.y);
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        newPoint.x = cos * point.x - sin * point.y;
        newPoint.y = sin * point.x + cos * point.y;
        return newPoint;
    }
}
