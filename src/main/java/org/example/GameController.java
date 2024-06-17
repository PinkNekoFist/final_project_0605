package org.example;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class GameController {

    static boolean running = false;
    static final double moveDistance = 0.3;
    static final double rotateAngle = 1;
    static final double rationToWorld = 0.005;
    static int moveRowConst = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        World world = new World(50, 50);
        Output out = new Output();
        // init terminal
        Terminal terminal = TerminalBuilder.builder()
                .jna(true)
                .build();
        int terminalWidth = terminal.getWidth();
        int terminalHeight = terminal.getHeight();
        try {
            terminal.enterRawMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NonBlockingReader reader = terminal.reader();

        // System.out.println("height" + terminalHeight + " width" + terminalWidth);
        // 50 208 in Hyprland full screen
        Camera camera = new Camera(40, 40, 60, 70, terminalWidth, 50);
        Screen screen = new Screen(terminalWidth * rationToWorld, terminalHeight * rationToWorld, new Point[terminalWidth], camera);

        start();
        while (running) {
            refresh(terminalWidth, terminalHeight, camera, screen, world.getMap(), out);
            int ch = reader.read();
            switch (ch) {
                case 'q':
                    stop();
                    break;
                case 'w':
                    camera.move(moveDistance);
                    break;
                case 's':
                    camera.move(-moveDistance);
                    break;
                case 'd':
                    camera.rotate(rotateAngle);
                    break;
                case 'a':
                    camera.rotate(-rotateAngle);
                    break;
                case  'j':
                    moveRowConst -= 1;
                    break;
                case 'k':
                    moveRowConst += 1;
                    break;
                default:
                    break;
            }
            Thread.sleep(10);
        }
    }

    private static void start() {
        running = true;
    }

    private static void stop() {
        running = false;
        // TODO
    }

    private static boolean isRunning() {
        return running;
    }

    private static void refresh(int width, int height, Camera camera, Screen screen, char[][] map, Output out) {
        Graphic graphic = new Graphic(width, height, camera, screen, map, moveRowConst);
        try {
            out.output(graphic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
