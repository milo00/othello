package com.company;

public class Logic extends Simulator {
    private static final int DIMENSIONS_OF_BOARD = 8;
    private static final int DRAW_SCORE = 0;
    private static final int INITIAL_COUNTER_VALUE = 0;

    public boolean makeMove(Color color, int row, int column) {
        Disk disk = board.popDiskFromHole(color);
        return move(color, row, column, true, disk).getFirst();
    }

    public boolean ifEnded() {
        return board.getHoleDisks(Color.BLACK) == 0 && board.getHoleDisks(Color.WHITE) == 0;
    }

    public void print() {
        board.print();
    }

    public Tuple<Color, Integer> whichColorWon() {
        int blackCounter = INITIAL_COUNTER_VALUE;
        int whiteCounter = INITIAL_COUNTER_VALUE;
        for (int i = 0; i < DIMENSIONS_OF_BOARD; i++) {
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                if (board.getColor(i, j) == Color.BLACK) {
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
            return new Tuple<>(Color.GREEN, DRAW_SCORE);
        }
    }

    public Color getSquareColor(int row, int column){
        return board.getColor(row, column);
    }
}
