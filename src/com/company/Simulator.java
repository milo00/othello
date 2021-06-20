package com.company;

import java.util.Random;

public class Simulator {
    private static final int INITIAL_BEST_VALUE = -1;
    private static final int MIN_SCORE = 0;
    private static final int MAX_DIMENSIONS = 8;
    private static final int UNSUCCESSFUL_MOVE_SCORE = 0;
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 7;
    private static final int INCREMENT_FACTOR = 1;
    private static final int INITIAL_CHANGED_DISKS = 0;

    protected static Board board = new Board();

    public Position simulate(Color color) {
        int bestRow = INITIAL_BEST_VALUE;
        int bestColumn = INITIAL_BEST_VALUE;
        int bestScore = INITIAL_BEST_VALUE;
        Tuple<Boolean, Integer> actualResult;
        Disk disk = board.peekDiskFromHole(color);

        for (int i = 0; i < MAX_DIMENSIONS; i++) {
            for (int j = 0; j < MAX_DIMENSIONS; j++) {
                actualResult = move(color, i, j, false, disk);
                if (actualResult.first && actualResult.second > bestScore) {
                    bestScore = actualResult.getSecond();
                    bestRow = i;
                    bestColumn = j;
                }
            }
        }

        if (bestScore == MIN_SCORE) {
            Random random = new Random();
            do {
                bestRow = random.nextInt(MAX_DIMENSIONS);
                bestColumn = random.nextInt(MAX_DIMENSIONS);
                actualResult = move(color, bestRow, bestColumn, false, disk);
            }
            while (!actualResult.first);
        }

        return new Position(bestRow, bestColumn);
    }

    protected Tuple<Boolean, Integer> move(Color color, int row, int column, boolean ifChange, Disk disk) {
        boolean success = board.put(row, column, disk, ifChange);
        if (!success) {
            if (ifChange) {
                board.pushDiskBackToHole(disk);
            }
            return new Tuple<>(false, UNSUCCESSFUL_MOVE_SCORE);
        } else {
            return new Tuple<>(true, spreadTheMove(color, row, column, ifChange));
        }
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
        boolean direction1 = board.getColor(Math.max(MIN_INDEX, row - INCREMENT_FACTOR),
                Math.max(MIN_INDEX, column - INCREMENT_FACTOR)) == oppositeColor;
        boolean direction2 = board.getColor(Math.max(MIN_INDEX, row - INCREMENT_FACTOR), column) == oppositeColor;
        boolean direction3 = board.getColor(Math.max(MIN_INDEX, row - INCREMENT_FACTOR),
                Math.min(MAX_INDEX, column + INCREMENT_FACTOR)) == oppositeColor;
        boolean direction4 = board.getColor(row, Math.min(7, column + INCREMENT_FACTOR)) == oppositeColor;
        boolean direction5 = board.getColor(Math.min(MAX_INDEX, row + INCREMENT_FACTOR),
                Math.min(MAX_INDEX, column + INCREMENT_FACTOR)) == oppositeColor;
        boolean direction6 = board.getColor(Math.min(MAX_INDEX, row + INCREMENT_FACTOR), column) == oppositeColor;
        boolean direction7 = board.getColor(Math.min(MAX_INDEX, row + INCREMENT_FACTOR),
                Math.max(MIN_INDEX, column - INCREMENT_FACTOR)) == oppositeColor;
        boolean direction8 = board.getColor(row, Math.max(MIN_INDEX, column - INCREMENT_FACTOR)) == oppositeColor;

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

        for (int i = 1; i < MAX_DIMENSIONS; i++) {
            if (direction1 && !goInDirection1 && row - INCREMENT_FACTOR - i >= MIN_INDEX
                    && column - INCREMENT_FACTOR - i >= MIN_INDEX) {
                currentColor = board.getColor(row - INCREMENT_FACTOR - i, column - INCREMENT_FACTOR - i);
                if (currentColor == Color.GREEN) {
                    direction1 = false;
                }
                goInDirection1 = currentColor == color;
            }
            if (direction2 && !goInDirection2) {
                currentColor = board.getColor(Math.max(MIN_INDEX, row - INCREMENT_FACTOR - i), column);
                if (currentColor == Color.GREEN) {
                    direction2 = false;
                }
                goInDirection2 = currentColor == color;
            }
            if (direction3 && !goInDirection3 && row - INCREMENT_FACTOR - i >= MIN_INDEX
                    && column + INCREMENT_FACTOR + i <= MAX_INDEX) {
                currentColor = board.getColor(row - INCREMENT_FACTOR - i, column + INCREMENT_FACTOR + i);
                if (currentColor == Color.GREEN) {
                    direction3 = false;
                }
                goInDirection3 = currentColor == color;
            }
            if (direction4 && !goInDirection4) {
                currentColor = board.getColor(row, Math.min(MAX_INDEX, column + INCREMENT_FACTOR + i));
                if (currentColor == Color.GREEN) {
                    direction4 = false;
                }
                goInDirection4 = currentColor == color;
            }
            if (direction5 && !goInDirection5 && row + INCREMENT_FACTOR + i <= MAX_INDEX
                    && column + INCREMENT_FACTOR + i <= MAX_INDEX) {
                currentColor = board.getColor(row + INCREMENT_FACTOR + i, column + INCREMENT_FACTOR + i);
                if (currentColor == Color.GREEN) {
                    direction5 = false;
                }
                goInDirection5 = currentColor == color;
            }
            if (direction6 && !goInDirection6) {
                currentColor = board.getColor(Math.min(MAX_INDEX, row + INCREMENT_FACTOR + i), column);
                if (currentColor == Color.GREEN) {
                    direction6 = false;
                }
                goInDirection6 = currentColor == color;
            }
            if (direction7 && !goInDirection7 && row + INCREMENT_FACTOR + i <= MAX_INDEX
                    && column - INCREMENT_FACTOR - i >= MIN_INDEX) {
                currentColor = board.getColor(row + INCREMENT_FACTOR + i, column - INCREMENT_FACTOR - i);
                if (currentColor == Color.GREEN) {
                    direction7 = false;
                }
                goInDirection7 = currentColor == color;
            }
            if (direction8 && !goInDirection8) {
                currentColor = board.getColor(row, Math.max(MIN_INDEX, column - INCREMENT_FACTOR - i));
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

        int changedDisks = INITIAL_CHANGED_DISKS;
        int i = row - INCREMENT_FACTOR, j = column - INCREMENT_FACTOR;
        while (goInDirection1 && i >= MIN_INDEX && j >= MIN_INDEX && board.getColor(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i--;
            j--;
            changedDisks++;
        }
        i = row - INCREMENT_FACTOR;
        while (goInDirection2 && i >= MIN_INDEX && board.getColor(i, column) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, column);
            }
            i--;
            changedDisks++;
        }
        i = row - INCREMENT_FACTOR;
        j = column + INCREMENT_FACTOR;
        while (goInDirection3 && i >= MIN_INDEX && j <= MAX_INDEX && board.getColor(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i--;
            j++;
            changedDisks++;
        }
        j = column + INCREMENT_FACTOR;
        while (goInDirection4 && j <= MAX_INDEX && board.getColor(row, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(row, j);
            }
            j++;
            changedDisks++;
        }
        i = row + INCREMENT_FACTOR;
        j = column + INCREMENT_FACTOR;
        while (goInDirection5 && i <= MAX_INDEX && j <= MAX_INDEX && board.getColor(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i++;
            j++;
            changedDisks++;
        }
        i = row + INCREMENT_FACTOR;
        while (goInDirection6 && i <= MAX_INDEX && board.getColor(i, column) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, column);
            }
            i++;
            changedDisks++;
        }
        i = row + INCREMENT_FACTOR;
        j = column - INCREMENT_FACTOR;
        while (goInDirection7 && i <= MAX_INDEX && j >= MIN_INDEX && board.getColor(i, j) == oppositeColor) {
            if (ifChange) {
                board.changeDiskColor(i, j);
            }
            i++;
            j--;
            changedDisks++;
        }
        j = column - INCREMENT_FACTOR;
        while (goInDirection8 && j >= MIN_INDEX && board.getColor(row, j) == oppositeColor) {
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
