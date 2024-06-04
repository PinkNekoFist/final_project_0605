package org.example;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        Input input = new Input();
        Camera camera = new Camera(0, 0, 0);
        gameController.start();

        while (gameController.isRunning()) {

        }
    }
}