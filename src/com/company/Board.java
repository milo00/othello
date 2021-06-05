package com.company;

public class Board {
    private static final int DIMENSIONS_OF_BOARD = 8;

    private static Square[][] fields;
    private static Hole hole1;
    private static Hole hole2;

    public Board(Color color) {
        fields = new Square[8][8];
        for (int i = 0; i < DIMENSIONS_OF_BOARD; i++) {
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                fields[i][j] = new Square();
            }
        }
        hole1 = new Hole(color);
        hole2 = color == Color.BLACK ? new Hole(Color.WHITE) : new Hole(Color.BLACK);
    }

    private Hole getHole(Color color) {
        if (color == hole1.getColor()) return hole1;
        else if (color == hole2.getColor()) return hole2;
        else return null;
    }

    public int getHoleDisks(Color color) {
        Hole hole = getHole(color);
        if (hole == null) {
            return -1;
        } else {
            return hole.getDisks();
        }
    }

    public Disk takeDiskFromHole(Color color) {
        Hole hole = getHole(color);
        if (hole == null) {
            return null;
        } else {
            return hole.takeDisk();
        }
    }

    public Color getPlayerColor(int player) {
        if (player == 1) return hole1.getColor();
        else if (player == 2) return hole2.getColor();
        else return null;
    }

    public Color getSquare(int x, int y) {
        return fields[x][y].getColor();
    }

    public boolean put(int x, int y, Disk disk) {
        return fields[x][y].put(disk);
    }

    public void print() {
        System.out.format("""
                ╭–––––––––––––––––––––––––––––––––––––––––––╮
                |                                           |
                |                     %s                    |
                |                                           |
                ╰–––––––––––––––––––––––––––––––––––––––––––╯            
                """, hole1);
        for (int i = 0; i < 8; i++) {
            System.out.print("     ");

            for (int j = 0; j < 8; j++) {
                System.out.print("╭–––╮");
            }
            System.out.println();
            System.out.print("  " + (i + 1) + "  ");
            for (int j = 0; j < 8; j++) {
                System.out.format("| %s |", fields[i][j]);
            }

            System.out.println();
            System.out.print("     ");
            for (int j = 0; j < 8; j++) {
                System.out.print("╰–––╯");
            }
            System.out.println();
        }
        System.out.print("     ");
        for (char j = 'A'; j <= 'H'; j++) {
            System.out.format("  %c  ", j);
        }
        System.out.println();
        System.out.format("""
                ╭–––––––––––––––––––––––––––––––––––––––––––╮
                |                                           |
                |                     %s                    |
                |                                           |
                ╰–––––––––––––––––––––––––––––––––––––––––––╯            
                """, hole2);
    }
}
