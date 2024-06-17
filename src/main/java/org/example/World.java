package org.example;

public class World {
    private int width;
    private int height;
    private char[][] map;
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        map = new char[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    map[y][x] = '#';
                } else {
                    map[y][x] = '.';
                }
            }
        }
        changeMap(20, 30, 20 ,30);
        changeMap(0, 9, 0 ,9);
        changeMap(40, 49, 40 ,49);
        changeMap(0, 9, 40 ,49);
        changeMap(40, 49, 0 ,9);
    }

    public void changeMap(int x1, int x2, int y1, int y2) {
        if (x1 < 0 || y1 < 0 || x1 > x2 || y1 > y2 || x2 >= width || y2 >= height) return;
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                map[j][i] = '#';
            }
        }
    }

    public char[][] getMap() {
        return map;
    }
}