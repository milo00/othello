package com.company;

import java.util.Scanner;

public class Server {
    private static final int PLAYER1_ID = 1;
    private static final int PLAYER2_ID = 2;
    private static final int PLAYER1_INDEX = 0;
    private static final int PLAYER2_INDEX = 1;
    private static final int DRAW_SCORE = 0;

    private final Logic logic;
    private final Player player1;
    private final Player player2;

    public Server() {
        Player[] players = chooseMode();
        player1 = players[PLAYER1_INDEX];
        player2 = players[PLAYER2_INDEX];

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
        while (!logic.ifEnded()) {
            position = choosePosition(player1, PLAYER1_ID);
            System.out.println("Player1 move from position: " + position);
            logic.print();

            position = choosePosition(player2, PLAYER2_ID);
            System.out.println("Player2 move from position: " + position);
            logic.print();
        }
        generateResults();
    }

    private Position choosePosition(Player player, int playerId) {
        Position position;
        boolean success;
        position = player.move(playerId);
        success = logic.makeMove(player.getColor(), position.getRow(), position.getColumn());

        while (!success) {
            System.out.println("You cannot chose that position - it has to be empty. Try again.");
            position = player.move(playerId);
            success = logic.makeMove(player.getColor(), position.getRow(), position.getColumn());

        }
        return position;
    }

    private void generateResults() {
        Simulator.Tuple<Color, Integer> results = logic.whichColorWon();

        if (results.getSecond() == DRAW_SCORE) {
            System.out.println("It's a draw! Nobody won!!");
            return;
        }

        int playerNumber = player1.getColor() == results.getFirst() ? PLAYER1_ID : PLAYER2_ID;
        System.out.println("Player" + playerNumber + " won with " + results.getSecond() + " points! Congrats!");
    }
}
