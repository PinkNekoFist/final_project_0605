package org.example;

import java.awt.*;

public class Graphic {
    public String[][] frame;
    public int width, height;

    public Graphic(int width, int height, Camera camera) {
        this.width = width;
        this.height = height;
        frame = new String[height][width];
    }

    private void rayCasting (int width, int height, String[][] frame, Camera camera) {
        for (int x = 0; x < width; x++) {

        }
    }
}
