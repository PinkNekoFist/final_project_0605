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
                frame[y][x] = '#';
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
            pointsAfter[i].x += camera.x;
            pointsAfter[i].y += camera.y;
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
            double dy = 1/sin;
            double dx = 1/cos;

            // find first two points
            Point px = findFirstPointX(pointsAfter[i], vector2D[i], sin, cos, tan);
            Point py = findFirstPointY(pointsAfter[i], vector2D[i], sin, cos, tan);
//            System.out.println("first point x : " + px.x + " y : " + px.y);
//            System.out.println("second point x : " + py.x + " y : " + py.y);
            // System.out.println(dx + " " + dy);

            distanceX += distanceBetweenPoints(pointsAfter[i], px);
            distanceY += distanceBetweenPoints(pointsAfter[i], py);

            checkHit:
            while (distanceX < camera.renderDistance || distanceY < camera.renderDistance) {
                while (distanceX <= distanceY && distanceX < camera.renderDistance) {
                    // System.out.println("checking hit");
                    // check if the point is hit wall
                    if (hitWall(vector2D[i], px, frame, screen, map)) {
                        System.out.println(px.x + " " + px.y);
                        break checkHit;
                    }
                    // next point
                    distanceX += dx;
                    px.x += 1;
                    px.y += tan;
                }

                while (distanceY <= distanceX && distanceY < camera.renderDistance) {
                    // check if the point is hit wall
                    if (hitWall(vector2D[i], py, frame, screen, map)) {
                        System.out.println(py.x + " " + py.y);
                        break checkHit;
                    }
                    // next point
                    distanceY += dy;
                    py.x += 1/tan;
                    py.y += 1;
                }
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

    private Point findFirstPointX (Point p, Point vector, double sin, double cos, double tan) {
        double x = (vector.x < 0) ? Math.floor(p.x) : Math.ceil(p.x);
        return new Point(x, p.y + (x - p.x) * tan);
    }

    private Point findFirstPointY (Point p, Point vector, double sin, double cos, double tan) {
        double y = (vector.y < 0) ? Math.floor(p.y) : Math.ceil(p.y);
        return new Point(p.x + (y - p.y) / tan, y);
    }

    private double distanceBetweenPoints (Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    private boolean hitWall (Point vector, Point p, char[][] frame, Screen screen, char[][] map) {
        int x = (int)p.x;
        int y = (int)p.y;
        if (vector.x < 0 && x == p.x) x -= 1;
        if (vector.y < 0 && y == p.y) y -= 1;
        if (x > map[0].length - 1 || 30 - y > map.length - 1) return false;
        return map[30 - y][x] == '#';
    }
}