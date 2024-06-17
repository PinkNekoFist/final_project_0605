package org.example;

import java.io.IOException;

interface Print {
    public void output(Graphic graphic) throws IOException;
    public void ansiPrint(int y, int x, char c) throws IOException;
    public void printAllScreen(char[][] frame) throws IOException;
}

public class Output implements Print {
    private char[][] frameLast;

    public Output () {

    }

    public void output(Graphic graphic) throws IOException {
        char[][] frame = graphic.getFrame();
        if (frameLast == null) {
            printAllScreen(frame);
            return;
        }
        for (int i = 0; i < frame.length; i++ ) {
            for (int j = frame[0].length - 1; j >= 0 ; j-- ) {
                if (frame[i][j] != frameLast[i][j]) {
                    ansiPrint(i, j, frame[i][j]);
                }
            }
        }
        frameLast = frame;
    }

    public void ansiPrint(int y,int x, char c) throws IOException {
        // Move the cursor to row y, column x
        String temp = String.format("\033[%d;%dH", y+1, x+1);
        System.out.print(temp);
        System.out.print(c);
    }

    public void printAllScreen (char[][] frame) throws IOException {
        System.out.print("\033[H");
        for (int i = 0; i < frame.length; i++ ) {
            for (int j = frame[0].length - 1; j >= 0; j-- ) {
                System.out.print(frame[i][j]);
            }
            if (i != frame.length - 1 ) System.out.println();
        }
        frameLast = frame;
    }
}