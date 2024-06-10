package org.example;


public class Graphic {
    public char[][] frame;
    public int width, height;
    private Point[] pointsOnScreen;

    public Graphic(int width, int height, Camera camera, Screen screen, char[][] map) {
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
        rayCasting(width, height, frame, camera, screen, map);
    }

    private void rayCasting (int width, int height, char[][] frame, Camera camera, Screen screen, char[][] map) {
        // terminal per line has width (208 while full screen in my pc) char
        // one char is n = 0.005 in map
        // first step : get every column's position in screen on map, by rotate a line and move it
        Point[] pointsAfter = new Point[screen.points.length];
        Point[] vector2D = new Point[screen.points.length];
        for (int i = 0;i < width; i++) {
            pointsAfter[i] = rotatePoint(screen.points[i], camera.angle);
            vector2D[i] = new Point(pointsAfter[i]);
            pointsAfter[i].x += camera.position.x;
            pointsAfter[i].y += camera.position.y;
            // System.out.println(pointsAfter[i].x + " " + pointsAfter[i].y);
        }

        // second step : find a vector from camera to points // vector2D[]

        // third step : according to vector find a wall on map (start from screen, end at wall or board)
        for (int i = 0;i < pointsAfter.length;i++) {
            double angle = Math.atan2(vector2D[i].y, vector2D[i].x);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double tan = sin/cos;
            double distanceY = 0; // y is Z
            double distanceX = 0; // x is Z
            double dy = Math.abs(1/sin);
            double dx = Math.abs(1/cos);

            // find first two points
            Point px = findFirstPointX(pointsAfter[i], vector2D[i], tan);
            Point py = findFirstPointY(pointsAfter[i], vector2D[i], tan);
//            System.out.println("first point x : " + px.x + " y : " + px.y);
//            System.out.println("second point x : " + py.x + " y : " + py.y);
            // System.out.println(dx + " " + dy);

            distanceX += distanceBetweenPoints(pointsAfter[i], px);
            distanceY += distanceBetweenPoints(pointsAfter[i], py);

            // TODO
            if (Double.isNaN(distanceY)) {
                distanceY = 0;
            }

            int directionX = (vector2D[i].x > 0) ? 1 : -1;
            int directionY = (vector2D[i].y > 0) ? 1 : -1;

            checkHit: {
                while (distanceX < camera.renderDistance || distanceY < camera.renderDistance) {
                    // System.out.println(distanceX + " " + distanceY);
                    while (distanceX <= distanceY && distanceX < camera.renderDistance) {
                        // System.out.println("checking hit");
                        // check if the point is hit wall
                        if (hitWall(vector2D[i], px, map)) {
                            // System.out.println(i + " hit " + px.x + " " + px.y + " " + tan);
                            render(i, distanceBetweenPoints(pointsAfter[i], camera.position), distanceX, frame);
                            break checkHit;
                        }
                        // next point
                        distanceX += dx;
                        px.x += directionX;
                        px.y += directionX * tan;
                        // System.out.println(i + distanceX + " " + distanceY);
                    }

                    while (distanceY <= distanceX && distanceY < camera.renderDistance) {
                        // check if the point is hit wall
                        if (hitWall(vector2D[i], py, map)) {
                            // System.out.println(i + " hit " + py.x + " " + py.y + " " + tan);
                            render(i, distanceBetweenPoints(pointsAfter[i], camera.position), distanceY, frame);
                            break checkHit;
                        }
                        // next point
                        distanceY += dy;
                        py.x += directionY / tan;
                        py.y += directionY;
                        // System.out.println(i + distanceX + " " + distanceY);
                    }
                }
                // System.out.println(i + " not hit");
                erase(i, frame);
            }
        }

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

    private Point findFirstPointX (Point p, Point vector, double tan) {
        double x = (vector.x < 0) ? Math.floor(p.x) : Math.ceil(p.x);
        return new Point(x, p.y + (x - p.x) * tan);
    }

    private Point findFirstPointY (Point p, Point vector, double tan) {
        double y = (vector.y < 0) ? Math.floor(p.y) : Math.ceil(p.y);
        return new Point(p.x + (y - p.y) / tan, y);
    }

    private double distanceBetweenPoints (Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    private boolean hitWall (Point vector, Point p, char[][] map) {
        int x = (int)p.x;
        int y = (int)p.y;
        if (vector.x < 0 && x == p.x) x -= 1;
        if (vector.y < 0 && y == p.y) y -= 1;
        if (x > map[0].length - 1 || 30 - y > map.length - 1 || x < 0 || y < 0) return false;
        return map[30 - y][x] == '#';
    }

    private void render (int column, double d1, double d2, char[][] frame) {
        // System.out.println(d1 + " " + d2);
        for (int i = (int)(d1 * -100 / (d1 + d2));i < (int)(d1 * 400 / (d1 + d2)) && i <= 35; i++) {
            if (i < -14) i = -14;
            frame[35 - i][column] = texture(d2);
        }
    }

    private void erase (int column, char[][] frame) {
        for (int i = 0; i < frame.length; i++) {
            frame[i][column] = ' ';
        }
    }

    private char texture (double distance) {
        if (distance < 10) return '@';
        else if (distance < 12) return '%';
        else if (distance < 15) return '#';
        else if (distance < 17) return '$';
        else if (distance < 20) return '+';
        else return '.';
    }
}