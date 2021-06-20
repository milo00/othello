package com.company;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private static final int CORRECT_POSITION_LENGTH = 2;

    public HumanPlayer(Color color) {
        super(color);
    }

    @Override
    public Position move(int id) {
        System.out.println("Player" + id + " choose position:");
        Scanner scanner = new Scanner(System.in);
        Position position = new Position();
        boolean ifSet;
        do {
            String line = scanner.nextLine();
            if (line.length() != CORRECT_POSITION_LENGTH) {
                System.out.println("Wrong input - first sign has to be a letter between A and H, second - number between 1 and 8");
                ifSet = false;
            } else {
                ifSet = position.set(line);

                if (position.ifRowIncorrect()) {
                    System.out.println("The first sign has to be a char between A and H");
                }
                if (position.ifColumnIncorrect()) {
                    System.out.println("The second sign has to be a number between 1 and 8");
                }
            }

        } while (!ifSet);
        return position;
    }
}
