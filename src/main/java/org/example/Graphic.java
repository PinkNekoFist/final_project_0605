package org.example;

interface Get {
    public char[][] getFrame();
}

public class Graphic implements Get{
    private char[][] frame;
    private int width, height;
    private int moveRowConst;

    public Graphic(int width, int height, Camera camera, Screen screen, char[][] map, int moveRowConst) {
        this.width = width;
        this.height = height;
        this.moveRowConst = 33 + moveRowConst;

        frame = new char[height][width];
        // test
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = ' ';
            }
        }

        // rayCasting
        rayCasting(frame, camera, screen, map);
    }

    private void rayCasting (char[][] frame, Camera camera, Screen screen, char[][] map) {
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
        }


        // second step : according to vector find a wall on map (start from screen, end at wall or board) and render it
        for (int i = 0;i < width;i++) {
            checkHit(camera, pointsAfter[i], vector2D[i], map, i);
        }
    }

    private void checkHit (Camera camera, Point pointsAfter, Point vector, char[][] map, int i) {
        double angle = Math.atan2(vector.y, vector.x);
        int directionX = (vector.x > 0) ? 1 : -1;
        int directionY = (vector.y > 0) ? 1 : -1;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double tan = sin/cos;
        
        double dy = Math.abs(1/sin);
        double dx = Math.abs(1/cos);
        
        // find first two points
        Point px = findFirstPointX(pointsAfter, vector, tan);
        Point py = findFirstPointY(pointsAfter, vector, tan);
        
        
        Point distance = new Point(distanceBetweenPoints(pointsAfter, px), distanceBetweenPoints(pointsAfter, py));

        while (distance.x < camera.renderDistance || distance.y < camera.renderDistance) {
            while (distance.x <= distance.y && distance.x < camera.renderDistance) {
                if (hitWall(vector, px, map)) {
                    render(i, height, distanceBetweenPoints(pointsAfter, camera.position), distance.x, frame, new Point(distance.x*cos, distance.x*sin), new Point(-1 * directionX, 0), moveRowConst);
                    return;
                }
                // next point
                distance.x += dx;
                px.x += directionX;
                px.y += directionX * tan;
            }

            while (distance.y <= distance.x && distance.y < camera.renderDistance) {
                if (hitWall(vector, py, map)) {
                    render(i, height, distanceBetweenPoints(pointsAfter, camera.position), distance.y, frame, new Point(distance.y*cos, distance.y*sin), new Point(0, -1 * directionY), moveRowConst);
                    return;
                }
                // next point
                distance.y += dy;
                py.x += directionY / tan;
                py.y += directionY;
            }
        }
        erase(i, frame);
    }

    private Point rotatePoint (Point point, double angle) {
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
        double distance = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
            if (Double.isNaN(distance)) {
                return 0;
            }
        return distance;
    }

    private boolean hitWall (Point vector, Point p, char[][] map) {
        int x = (int)p.x;
        int y = (int)p.y;
        if (vector.x < 0 && x == p.x) x -= 1;
        if (vector.y < 0 && y == p.y) y -= 1;
        if (x > map[0].length - 1 || map.length - y > map.length - 1 || x < 0 || y < 0) return false;
        return map[map.length - y][x] == '#';
    }

    private void render (int column, int numOfRow, double d1, double d2, char[][] frame,Point lightVector, Point normalVector, int moveRowConst) {
        double wallHeight = numOfRow * 12;
        double ratio = 0.8;
        int moveRow = (int)(wallHeight * ratio) - moveRowConst;
        int startHeight = (int)(wallHeight * ratio * (1 - d1 / (d1 + d2))) - moveRow;
        int endHeight = (int)(wallHeight * ratio * (1 + (1 - ratio) * d1 / (d1 +d2))) - moveRow;
        for (int i = startHeight;i < endHeight; i++) {
            if (i < 0 || i >= height) continue;
            frame[i][column] = texture(lightVector, normalVector, d2);
        }
    }

    private void erase (int column, char[][] frame) {
        for (int i = 0; i < height; i++) {
            frame[i][column] = ' ';
        }
    }

    // private static final String lightTexture = ".'`^\\\",:;Il!i><~+_-?][}{1)(|/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao#MW&8%B@$";
    private  static final String lightTexture = ".,-~:;=!*#$@";
    private char texture (Point lightVector, Point normalVector, double distance) {
        lightVector.normalize();
        double dot = lightVector.x * normalVector.x + lightVector.y * normalVector.y;
        int temp = (int)(dot * -12) - (int)(distance * 12 / 50);
        if (temp < 0) {
            temp = 0;
        } if (temp > 11) {
            temp = 11;
        }
        return lightTexture.charAt(temp);
    }

    public char[][] getFrame() {
        return frame;
    }
}
