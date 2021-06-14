package com.company;

public class Logic extends Simulator {

    public Logic() {
        super();
    }

    public boolean makeMove(Player player, int row, int column) {
        Disk disk = board.takeDiskFromHole(player.getColor());
        return move(player, row, column, true, disk).getFirst();
    }

    public void print() {
        board.print();
    }

    public Tuple<Color, Integer> whichColorWon() {
        int blackCounter = 0;
        int whiteCounter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.checkColor(i, j) == Color.BLACK) {
                    blackCounter++;
                } else {
                    whiteCounter++;
                }
            }
        }

        if (blackCounter > whiteCounter) {
            return new Tuple<>(Color.BLACK, blackCounter - whiteCounter);
        } else if (whiteCounter > blackCounter) {
            return new Tuple<>(Color.WHITE, whiteCounter - blackCounter);
        } else {
            return new Tuple<>(Color.GREEN, 0);
        }
    }
}
