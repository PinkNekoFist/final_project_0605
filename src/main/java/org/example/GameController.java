package org.example;

import org.jline.terminal.Terminal;

import java.io.IOException;

public class GameController {

    static boolean running = false;
    static final double moveDistance = 1;
    static final double rotateAngle = 0.1;

    public static void main(String[] args) throws IOException {
        World world = new World(30, 30);
        Input input = new Input();
        Output out = new Output();
        Terminal terminal;
        Camera camera = new Camera(15, 15, 0, 70, terminal.getWidth());

        start();
        while (running) {
            if () {
                camera.move(moveDistance);
            }

            if () {
                camera.rotate(rotateAngle);
            }

            Graphic frame = new Graphic(terminal.getWidth(), terminal.getHeight(), camera);
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
