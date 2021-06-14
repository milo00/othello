package com.company;

import java.util.Random;

public class Simulator {
    protected static Board board;

    public Simulator() {
        board = new Board();
    }

    public int[] simulate(Player player) {
        int bestRow = -1;
        int bestColumn = -1;
        int bestScore = -1;
        Tuple<Boolean, Integer> actualResult;
        Disk disk = board.takeDiskFromHole(player.getColor());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                actualResult = move(player, i, j, false, disk);
                if (actualResult.first && actualResult.second > bestScore) {
                    bestScore = actualResult.getSecond();
                    bestRow = i;
                    bestColumn = j;
                }
            }
        }
        System.out.println(bestScore);

        if (bestScore == 0) {
            Random random = new Random();
            bestRow = random.nextInt(8);
            bestColumn = random.nextInt(8);
            actualResult = move(player, bestRow, bestColumn, false, disk);
            while (!actualResult.first) {
                bestRow = random.nextInt(8);
                bestColumn = random.nextInt(8);
                actualResult = move(player, bestRow, bestColumn, false, disk);
            }
        }

        board.pushDiskBackToHole(disk);
        return new int[]{bestRow, bestColumn};
    }

    protected Tuple<Boolean, Integer> move(Player player, int row, int column, boolean ifChange, Disk disk) {
        boolean success = board.put(row, column, disk, ifChange);
        if (!success) {
            if (ifChange) {
                board.pushDiskBackToHole(disk);
            }
            return new Tuple<>(false, 0);
        } else {
            return new Tuple<>(true, spreadTheMove(player.getColor(), row, column, ifChange));
        }
    }

    public boolean ifEnded() {
        return board.getHoleDisks(Color.BLACK) == 0 && board.getHoleDisks(Color.WHITE) == 0;
    }

    private int spreadTheMove(Color color, int row, int column, boolean ifChange) {
        /*
           1    2    3
             *******
           8 *  X  * 4
             *******
           7    6    5
         */

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

        Color currentColor;

        for (int i = 1; i < 8; i++) {
            if (direction1 && !goInDirection1) {
                currentColor = board.getSquare(Math.max(0, row - 1 - i), Math.max(0, column - 1 - i));
                if (currentColor == Color.GREEN) {
                    direction1 = false;
                }
                goInDirection1 = currentColor == color;
            }
            if (direction2 && !goInDirection2) {
                currentColor = board.getSquare(Math.max(0, row - 1 - i), column);
                if (currentColor == Color.GREEN) {
                    direction2 = false;
                }
                goInDirection2 = currentColor == color;
            }
            if (direction3 && !goInDirection3) {
                currentColor = board.getSquare(Math.max(0, row - 1 - i), Math.min(7, column + 1 + i));
                if (currentColor == Color.GREEN) {
                    direction3 = false;
                }
                goInDirection3 = currentColor == color;
            }
            if (direction4 && !goInDirection4) {
                currentColor = board.getSquare(row, Math.min(7, column + 1 + i));
                if (currentColor == Color.GREEN) {
                    direction4 = false;
                }
                goInDirection4 = currentColor == color;
            }
            if (direction5 && !goInDirection5) {
                currentColor = board.getSquare(Math.min(7, row + 1 + i), Math.min(7, column + 1 + i));
                if (currentColor == Color.GREEN) {
                    direction5 = false;
                }
                goInDirection5 = currentColor == color;
            }
            if (direction6 && !goInDirection6) {
                currentColor = board.getSquare(Math.min(7, row + 1 + i), column);
                if (currentColor == Color.GREEN) {
                    direction6 = false;
                }
                goInDirection6 = currentColor == color;
            }
            if (direction7 && !goInDirection7) {
                currentColor = board.getSquare(Math.min(7, row + 1 + i), Math.max(0, column - 1 - i));
                if (currentColor == Color.GREEN) {
                    direction7 = false;
                }
                goInDirection7 = currentColor == color;
            }
            if (direction8 && !goInDirection8) {
                currentColor = board.getSquare(row, Math.max(0, column - 1 - i));
                if (currentColor == Color.GREEN) {
                    direction8 = false;
                }
                goInDirection8 = currentColor == color;
            }
        }

        /*
           1    2    3
             *******
           8 *  X  * 4
             *******
           7    6    5
         */

        int changedDisks = 0;
        int i = row - 1, j = column - 1;
        while (goInDirection1 && i >= 0 && j >= 0 && board.getSquare(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i--;
            j--;
            changedDisks++;
        }
        i = row - 1;
        while (goInDirection2 && i >= 0 && board.getSquare(i, column) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, column);
            }
            i--;
            changedDisks++;
        }
        i = row - 1;
        j = column + 1;
        while (goInDirection3 && i >= 0 && j < 8 && board.getSquare(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i--;
            j++;
            changedDisks++;
        }
        j = column + 1;
        while (goInDirection4 && j < 8 && board.getSquare(row, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(row, j);
            }
            j++;
            changedDisks++;
        }
        i = row + 1;
        j = column + 1;
        while (goInDirection5 && i < 8 && j < 8 && board.getSquare(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i++;
            j++;
            changedDisks++;
        }
        i = row + 1;
        while (goInDirection6 && i < 8 && board.getSquare(i, column) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, column);
            }
            i++;
            changedDisks++;
        }
        i = row + 1;
        j = column - 1;
        while (goInDirection7 && i < 8 && j >= 0 && board.getSquare(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i++;
            j--;
            changedDisks++;
        }
        j = column - 1;
        while (goInDirection8 && j >= 0 && board.getSquare(row, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(row, j);
            }
            j--;
            changedDisks++;
        }

        return changedDisks;
    }

    protected static class Tuple<T, S> {
        private final T first;
        private final S second;

        public Tuple(T first, S second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
    }
}
