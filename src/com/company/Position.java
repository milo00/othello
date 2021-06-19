package com.company;

public class Position {

    private static final int INCORRECT_VALUE = -1;
    private int row;
    private int column;

    public Position() {
        row = INCORRECT_VALUE;
        column = INCORRECT_VALUE;
    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean set(String positions) {
        if (positions.length() != 2) {
            return false;
        } else {
            column = switch (positions.charAt(0)) {
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
                row = Integer.parseInt(positions.substring(1)) - 1;

                if (row < 0 || row > 7) {
                    row = INCORRECT_VALUE;
                    return false;
                }

                return true;
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

        return pos1 + (row + 1);
    }
}
