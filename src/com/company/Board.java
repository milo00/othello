package com.company;

public class Board {
    private static final int DIMENSIONS_OF_BOARD = 8;

    private static Square[][] fields;
    private static Hole hole1;
    private static Hole hole2;

    public Board() {
        fields = new Square[8][8];
        for (int i = 0; i < DIMENSIONS_OF_BOARD; i++) {
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                fields[i][j] = new Square();
            }
        }
        hole1 = new Hole(Color.BLACK);
        hole2 = new Hole(Color.WHITE);
    }

    public void print() {
        System.out.format("""
                ╭––––––––––––––––––––––––––––––––––––––╮
                |                                      |
                |                   %s                 |
                |                                      |
                ╰––––––––––––––––––––––––––––––––––––––╯            
                """, hole1);
        for (Square[] squareRow : fields) {
            for (int i = 0; i < 8; i++) {
                System.out.print("╭–––╮");
            }
            System.out.println();
            for (int i = 0; i < 8; i++) {
                System.out.format("| %s |", squareRow[i]);
            }
            System.out.println();
            for (int i = 0; i < 8; i++) {
                System.out.print("╰–––╯");
            }
            System.out.println();
        }
        System.out.format("""
                ╭––––––––––––––––––––––––––––––––––––––╮
                |                                      |
                |                   %s                 |
                |                                      |
                ╰––––––––––––––––––––––––––––––––––––––╯            
                """, hole2);
    }
}
