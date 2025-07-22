package server;

import java.util.Scanner;

public class Main {

    private static final String[] data = new String[1000];

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                String request = in.nextLine();
                if ("exit".equals(request)) return;
                processRequest(request);
            }
        }
    }

    private static void processRequest(String request) {
        String[] tokens = request.split("\\s+", 3);
        try {
            int index = Integer.parseInt(tokens[1]);
            if ("set".equals(tokens[0]) && tokens.length == 3) {
                data[index - 1] = tokens[2];
                System.out.println("OK");
            } else if ("get".equals(tokens[0]) && tokens.length == 2) {
                String text = data[index - 1];
                System.out.println(text == null ? "ERROR" : text);
            } else if ("delete".equals(tokens[0]) && tokens.length == 2) {
                data[index - 1] = null;
                System.out.println("OK");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("ERROR");
        }
    }
}

