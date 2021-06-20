package com.company;

public class Logic extends Simulator {

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
        int blackCounter = 0;
        int whiteCounter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
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
            return new Tuple<>(Color.GREEN, 0);
        }
    }

    public Board getBoard(){
        return board;
    }
}
