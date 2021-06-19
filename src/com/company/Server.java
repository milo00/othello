package com.company;

import java.util.Scanner;

public class Server {
    private final Logic logic;
    private final Player player1;
    private final Player player2;

    public Server() {
        Player[] players = chooseMode();
        player1 = players[0];
        player2 = players[1];

        this.logic = new Logic();
    }

    private Player[] chooseMode() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Choose mode:\n1 - player vs player\n2 - player vs computer\n3 - computer vs computer");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    return new Player[]{new HumanPlayer(Color.WHITE), new HumanPlayer(Color.BLACK)};
                }
                case "2" -> {
                    return new Player[]{new HumanPlayer(Color.WHITE), new Computer(Color.BLACK)};
                }
                case "3" -> {
                    return new Player[]{new Computer(Color.WHITE), new Computer(Color.BLACK)};
                }
                default -> System.out.println("Wrong number. Try again.");
            }
        }
    }

    public void play() {
        logic.print();
        Position position;
        boolean success;
        while (!logic.ifEnded()) {
            position = player1.move(1);
            success = logic.makeMove(player1, position.getRow(), position.getColumn());
            while (!success) {
                System.out.println("You cannot chose that position - it has to be empty. Try again.");
                position = player1.move(1);
                success = logic.makeMove(player1, position.getRow(), position.getColumn());

            }
            System.out.println("Player1 move from position: " + position);
            logic.print();

            position = player2.move(2);
            success = logic.makeMove(player2, position.getRow(), position.getColumn());

            while (!success) {
                System.out.println("You cannot chose that position - it has to be empty. Try again.");
                position = player2.move(2);
                success = logic.makeMove(player2, position.getRow(), position.getColumn());

            }
            System.out.println("Player2 move from position: " + position);
            logic.print();
        }
        generateResults();
    }

    private void generateResults() {
        Simulator.Tuple<Color, Integer> results = logic.whichColorWon();

        if (results.getSecond() == 0) {
            System.out.println("It's a draw! Nobody won!!");
            return;
        }

        int playerNumber = player1.getColor() == results.getFirst() ? 1 : 2;
        System.out.println("Player" + playerNumber + " won with " + results.getSecond() + " points! Congrats!");
    }
}
