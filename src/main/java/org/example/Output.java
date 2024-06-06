package org.example;

import org.jline.utils.AnsiWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Output {
    private Graphic frameLast;

    public Output () {

    }

    public void output(Graphic frame) throws IOException {
        AnsiWriter ansiWriter = new AnsiWriter(new OutputStreamWriter(System.out));
        if (frameLast == null) {
            printAllScreen(ansiWriter, frame);
            return;
        }
        for (int i = 0; i < frame.height; i++ ) {
            for (int j = 0; j < frame.width; j++ ) {
                if (frame.frame[i][j] != frameLast.frame[i][j]) {
                    ansiPrint(ansiWriter, i, j, frame.frame[i][j]);
                }
            }
        }
        frameLast = frame;
    }

    public void ansiPrint(AnsiWriter ansiWriter, int y,int x, char c) throws IOException {
        // Move the cursor to row 5, column 10
        String temp = String.format("\033[%d;%dH", y, x);
        ansiWriter.write(temp);
        // write
        ansiWriter.write(c);

        // Reset to default attributes
        ansiWriter.write("\033[0m");
        ansiWriter.flush();
    }

    public void printAllScreen (AnsiWriter ansiWriter, Graphic frame) throws IOException {
        ansiWriter.write("\033[H");
        for (int i = 0; i < frame.height; i++ ) {
            for (int j = 0; j < frame.width; j++ ) {
                System.out.print(frame.frame[i][j]);
            }
            System.out.println();
        }
        frameLast = frame;
        ansiWriter.write("\033[0m");
        ansiWriter.flush();
    }
}