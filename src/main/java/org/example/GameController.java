package org.example;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class GameController {

    static boolean running = false;
    static final double moveDistance = 1;
    static final double rotateAngle = 0.1;
    static final double rationToWorld = 0.005;

    public static void main(String[] args) throws IOException {
        World world = new World(30, 30);
        Input input = new Input();
        Output out = new Output();
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        int terminalWidth = terminal.getWidth();
        int terminalHeight = terminal.getHeight();
        System.out.println("height" + terminal.getHeight() + "width" + terminal.getWidth());
        // 50 208 in Hyprland full screen
        Camera camera = new Camera(0, 0, 90, 70, terminal.getWidth());
        Screen screen = new Screen(terminalWidth * rationToWorld, terminalHeight * rationToWorld, new Point[terminalWidth], camera);

        start();
        // Graphic frameTest = new Graphic(terminal.getWidth(), terminal.getHeight(), camera, screen);
        // while
        while (!running) {
//            if () {
//                camera.move(moveDistance);
//            }
//
//            if () {
//                camera.rotate(rotateAngle);
//            }

            Graphic frame = new Graphic(terminal.getWidth(), terminal.getHeight(), camera, screen);
            out.output(frame);
        }
    }

    public static void start() {
        running = true;
    }

    public static void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
