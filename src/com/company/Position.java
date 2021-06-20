package com.company;

import java.util.Objects;

public class Position {
    private static final int INCORRECT_VALUE = -1;
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 7;
    private static final int CORRECT_POSITION_LENGTH = 2;
    private static final int DIFF_BETWEEN_USER_INPUT_AND_APP_LOGIC = 1;
    private static final int LETTER_INDEX = 0;
    private static final int NUMBER_INDEX = 1;

    private int row;
    private int column;

    public Position() {
        row = INCORRECT_VALUE;
        column = INCORRECT_VALUE;
    }

    public Position(int row, int column) {
        if (row < MIN_INDEX || row > MAX_INDEX) {
            this.row = INCORRECT_VALUE;
        } else {
            this.row = row;
        }
        if (column < MIN_INDEX || column > MAX_INDEX) {
            this.column = INCORRECT_VALUE;
        } else {
            this.column = column;
        }
    }

    public boolean set(String positions) {
        if (positions.length() != CORRECT_POSITION_LENGTH) {
            return false;
        } else {
            column = switch (positions.charAt(LETTER_INDEX)) {
                case 'A' -> 0;
                case 'B' -> 1;
                case 'C' -> 2;
                case 'D' -> 3;
                case 'E' -> 4;
                case 'F' -> 5;
                case 'G' -> 6;
                case 'H' -> 7;
                default -> INCORRECT_VALUE;
            };

            try {
                row = Integer.parseInt(positions.substring(NUMBER_INDEX)) - DIFF_BETWEEN_USER_INPUT_AND_APP_LOGIC;

                if (row < MIN_INDEX || row > MAX_INDEX) {
                    row = INCORRECT_VALUE;
                    return false;
                }

                return column != INCORRECT_VALUE;
            } catch (NumberFormatException e) {
                row = INCORRECT_VALUE;
                return false;
            }
        }
    }

    public boolean ifRowIncorrect() {
        return row == INCORRECT_VALUE;
    }

    public boolean ifColumnIncorrect() {
        return column == INCORRECT_VALUE;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        String pos1 = switch (column) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            default -> "";
        };

        return pos1 + (row + DIFF_BETWEEN_USER_INPUT_AND_APP_LOGIC);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
