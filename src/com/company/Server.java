package com.company;

import java.util.Scanner;

public class Server {
    private final Logic logic;
    private final Player player1;
    private final Player player2;

    public Server() {
        Color color = chooseColor();
        this.logic = new Logic(color);

        Color oppositeColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        player1 = new Player(color);
        player2 = new Player(oppositeColor);
    }

    public Color chooseColor() {
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
