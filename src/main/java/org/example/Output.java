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
        for (int i = 0; i < frame.height; i++ ) {
            for (int j = 0; j < frame.width; j++ ) {
                if (frame.frame[i][j].equals(frameLast.frame[i][j])) {
                    ansiPrint(ansiWriter, i, j, frame.frame[i][j]);
                }
            }
        }
        frameLast = frame;
    }

    public void ansiPrint(AnsiWriter ansiWriter, int y,int x, String c) throws IOException {
        // Move the cursor to row 5, column 10
        String temp = String.format("\033[%d;%dH", y, x);
        ansiWriter.write(temp);
        // write
        ansiWriter.write(c);

        // Reset to default attributes
        ansiWriter.write("\033[0m");
        ansiWriter.flush();
    }
}
