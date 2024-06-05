package org.example;

public class World {
    private int width;
    private int height;
    private char[][] map;
    public World(int width, int height) {
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
    }

    public void changeMap(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        } else {
            map[y][x] = '#';
        }
    }

    public char[][] getMap() {
        return map;
    }
}