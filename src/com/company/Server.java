package com.company;

import java.util.Scanner;

public class Server {
    private final Logic logic;
    private final Player player1;
    private final Player player2;

    public Server() {
        Player players[] = chooseMode();
        player1 = players[0];
        player2 = players[1];

        this.logic = new Logic(player1.getColor());
    }

    private Player[] chooseMode() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Choose mode:\n1 - player vs player\n2 - player vs computer\n3 - computer vs computer");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    Color color = chooseColor();
                    Color oppositeColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
                    return new Player[]{new HumanPlayer(color), new HumanPlayer(oppositeColor)};
                }
                case "2" -> {
                    Color color = chooseColor();
                    Color oppositeColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
                    return new Player[]{new HumanPlayer(color), new Computer(oppositeColor)};
                }
                case "3" -> {
                    return new Player[]{new Computer(Color.WHITE), new Computer(Color.BLACK)};
                }
                default -> System.out.println("Wrong number. Try again.");
            }
        }
    }

    private Color chooseColor() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Player1 - choose which color you want to play (B - black, W - white):");
            input = scanner.nextLine();
            if (input.equals("W")) {
                return Color.WHITE;
            } else if (input.equals("B")) {
                return Color.BLACK;
            } else {
                System.out.println("Wrong input. Try again.");
            }
        }
    }

    public void play() {
        logic.print();
        int[] position;
        boolean success;
        position = player1.move(1);
        logic.makeMove(player1, position[0], position[1]);
        logic.print();
        while (!logic.ifEnded()) {
            position = player2.move(2);
            success = logic.makeMove(player2, position[0], position[1]);
            while (!success) {
                System.out.println("You cannot chose that position - it has to be empty. Try again.");
                position = player2.move(2);
                success = logic.makeMove(player2, position[0], position[1]);
            }
            logic.print();
            position = player1.move(1);
            success = logic.makeMove(player1, position[0], position[1]);
            while (!success) {
                System.out.println("You cannot chose that position - it has to be empty. Try again.");
                position = player1.move(1);
                success = logic.makeMove(player1, position[0], position[1]);
            }
            logic.print();
        }
    }
}
