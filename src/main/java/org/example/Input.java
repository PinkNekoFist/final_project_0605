package org.example;

import org.jline.terminal.*;
import org.jline.utils.NonBlockingReader;

public class Input {
    public static void main(String[] args) {
        try {
            Terminal terminal = TerminalBuilder.builder()
                    .jna(true)
                    .build();
            NonBlockingReader reader = terminal.reader();
            System.out.println("Press any key:");

            while (true) {
                int ch = reader.read();
                if (ch != -1)
                    System.out.println("Key pressed: " + (char) ch);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}