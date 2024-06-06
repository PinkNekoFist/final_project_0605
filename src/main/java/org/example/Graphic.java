package org.example;

import java.awt.*;

public class Graphic {
    public char[][] frame;
    public int width, height;

    public Graphic(int width, int height, Camera camera) {
        this.width = width;
        this.height = height;
        frame = new char[height][width];
        // test
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = ' ';
            }
        }

        // rayCasting
        rayCasting(width, height, frame, camera);
    }

    private void rayCasting (int width, int height, char[][] frame, Camera camera) {
        // terminal per line has width (208 while full screen in my pc) char
        // one char is n = 0.005 in map
        for (int x = 0; x < width; x++) {

        }
    }
}
