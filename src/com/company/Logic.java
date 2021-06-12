package com.company;

public class Logic {
    private final Board board;

    public Logic(Color color) {
        this.board = new Board(color);
    }

    //TODO: another idea
    public boolean makeMove(Player player, int row, int column) {
        Disk disk = board.takeDiskFromHole(player.getColor());
        boolean success = board.put(row, column, disk);
        if (!success) {
            board.pushDiskBackToHole(disk);
        } else {
            spreadTheMove(player.getColor(), row, column);
        }
        return success;
    }

    public boolean ifEnded() {
        return board.getHoleDisks(Color.BLACK) == 0 && board.getHoleDisks(Color.WHITE) == 0;
    }

    public void print() {
        board.print();
    }

    public void spreadTheMove(Color color, int row, int column) {
        /*
           1    2    3
             *******
           8 *  X  * 4
             *******
           7    6    5
         */


        /*System.out.println("row: " + row + " column: " + column);
        System.out.println("1 dir: " + board.getSquare(Math.max(0, row - 1), Math.max(0, column - 1)));
        System.out.println("2 dir: " + board.getSquare(Math.max(0, row - 1), column));
        System.out.println("3 dir: " + board.getSquare(Math.max(0, row - 1), Math.min(7, column + 1)));
        System.out.println("4 dir: " + board.getSquare(row, Math.min(7, column + 1)));
        System.out.println("5 dir: " + board.getSquare(Math.min(7, row + 1), Math.min(7, column + 1)));
        System.out.println("6 dir: " + board.getSquare(Math.min(7, row + 1), column));
        System.out.println("7 dir: " + board.getSquare(Math.min(7, row + 1), Math.max(0, column - 1)));
        System.out.println("8 dir: " + board.getSquare(row, Math.max(0, column - 1)));*/
        Color oppositeColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
        boolean direction1 = board.getSquare(Math.max(0, row - 1), Math.max(0, column - 1)) == oppositeColor;
        boolean direction2 = board.getSquare(Math.max(0, row - 1), column) == oppositeColor;
        boolean direction3 = board.getSquare(Math.max(0, row - 1), Math.min(7, column + 1)) == oppositeColor;
        boolean direction4 = board.getSquare(row, Math.min(7, column + 1)) == oppositeColor;
        boolean direction5 = board.getSquare(Math.min(7, row + 1), Math.min(7, column + 1)) == oppositeColor;
        boolean direction6 = board.getSquare(Math.min(7, row + 1), column) == oppositeColor;
        boolean direction7 = board.getSquare(Math.min(7, row + 1), Math.max(0, column - 1)) == oppositeColor;
        boolean direction8 = board.getSquare(row, Math.max(0, column - 1)) == oppositeColor;

        //after this loop, if false -> I have to change disks in this direction
        boolean goInDirection1 = false;
        boolean goInDirection2 = false;
        boolean goInDirection3 = false;
        boolean goInDirection4 = false;
        boolean goInDirection5 = false;
        boolean goInDirection6 = false;
        boolean goInDirection7 = false;
        boolean goInDirection8 = false;

        for (int i = 1; i < 8; i++) {
            if (direction1 && !goInDirection1) {
                goInDirection1 = board.getSquare(Math.max(0, row - 1 - i), Math.max(0, column - 1 - i)) == color;
            }
            if (direction2 && !goInDirection2) {
                goInDirection2 = board.getSquare(Math.max(0, row - 1 - i), column) == color;
            }
            if (direction3 && !goInDirection3) {
                goInDirection3 = board.getSquare(Math.max(0, row - 1 - i), Math.min(7, column + 1 + i)) == color;
            }
            if (direction4 && !goInDirection4) {
                goInDirection4 = board.getSquare(row, Math.min(7, column + 1 + i)) == color;
            }
            if (direction5 && !goInDirection5) {
                goInDirection5 = board.getSquare(Math.min(7, row + 1 + i), Math.min(7, column + 1 + i)) == color;
            }
            if (direction6 && !goInDirection6) {
                goInDirection6 = board.getSquare(Math.min(7, row + 1 + i), column) == color;
            }
            if (direction7 && !goInDirection7) {
                goInDirection7 = board.getSquare(Math.min(7, row + 1 + i), Math.max(0, column - 1 - i)) == color;
            }
            if (direction8 && !goInDirection8) {
                goInDirection8 = board.getSquare(row, Math.max(0, column - 1 - i)) == color;
            }
        }

        /*
           1    2    3
             *******
           8 *  X  * 4
             *******
           7    6    5
         */

        int i = row - 1, j = column - 1;
        while (goInDirection1 && i >= 0 && j >= 0 && board.getSquare(i, j) != color) {
            board.changeDiskColor(i, j);
            i--;
            j--;
        }
        i = row - 1;
        while (goInDirection2 && i >= 0 && board.getSquare(i, column) != color) {
            board.changeDiskColor(row, column);
            i--;
        }
        i = row - 1;
        j = column + 1;
        while (goInDirection3 && i >= 0 && j < 8 && board.getSquare(i, j) != color) {
            board.changeDiskColor(i, j);
            i--;
            j++;
        }
        j = column + 1;
        while (goInDirection4 && j < 8 && board.getSquare(row, j) != color) {
            board.changeDiskColor(row, j);
            j++;
        }
        i = row + 1;
        j = column + 1;
        while (goInDirection5 && i < 8 && j < 8 && board.getSquare(i, j) != color) {
            board.changeDiskColor(i, j);
            i++;
            j++;
        }
        i = row + 1;
        while (goInDirection6 && i < 8 && board.getSquare(i, column) != color) {
            board.changeDiskColor(i, column);
            i++;
        }
        i = row + 1;
        j = column - 1;
        while (goInDirection7 && i < 8 && j >= 0 && board.getSquare(i, j) != color) {
            board.changeDiskColor(i, j);
            i++;
            j--;
        }
        j = column - 1;
        while (goInDirection8 && j >= 0 && board.getSquare(row, j) != color) {
            board.changeDiskColor(row, j);
            j--;
        }

    }
}
