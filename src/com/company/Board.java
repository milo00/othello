package com.company;

public class Board {
    private static final int DIMENSIONS_OF_BOARD = 8;
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 7;

    private final Square[][] fields;
    private final Hole hole1;
    private final Hole hole2;

    public Board() {
        fields = new Square[DIMENSIONS_OF_BOARD][DIMENSIONS_OF_BOARD];
        for (int i = 0; i < DIMENSIONS_OF_BOARD; i++) {
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                fields[i][j] = new Square();
            }
        }
        hole1 = new Hole(Color.WHITE);
        hole2 = new Hole(Color.BLACK);
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

    public Disk peekDiskFromHole(Color color) {
        Hole hole = getHole(color);
        if (hole == null) {
            return null;
        } else {
            return hole.peekDisk();
        }
    }

    public Disk popDiskFromHole(Color color) {
        Hole hole = getHole(color);
        if (hole == null) {
            return null;
        } else {
            return hole.popDisk();
        }
    }

    public boolean pushDiskBackToHole(Disk disk) {
        if (disk == null) {
            return false;
        } else {
            Hole hole = getHole(disk.getColor());
            if (hole == null) {
                return false;
            } else {
                hole.pushDisk(disk);
                return true;
            }
        }
    }

    public boolean put(int row, int column, Disk disk, boolean ifChange) {
        if (row < MIN_INDEX || column < MIN_INDEX || row > MAX_INDEX || column > MAX_INDEX || disk == null) {
            return false;
        } else {
            return fields[row][column].put(disk, ifChange);
        }
    }

    public boolean changeDiskColor(int row, int column) {
        if (row < MIN_INDEX || column < MIN_INDEX || row > MAX_INDEX || column > MAX_INDEX) {
            return false;
        } else {
            Disk disk = fields[row][column].getDisk();
            if (disk != null) {
                disk.changeColor();
                return true;
            } else {
                return false;
            }
        }
    }

    public Color getColor(int row, int column) {
        if (row < MIN_INDEX || column < MIN_INDEX || row > MAX_INDEX || column > MAX_INDEX) {
            return null;
        } else {
            return fields[row][column].getColor();
        }
    }

    public void print() {
        System.out.format("""
                ╭–––––––––––––––––––––––––––––––––––––––––––╮
                |                                           |
                |                     %s                    |
                |                                           |
                ╰–––––––––––––––––––––––––––––––––––––––––––╯            
                """, hole2);
        for (int i = 0; i < DIMENSIONS_OF_BOARD; i++) {
            System.out.print("     ");

            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                System.out.print("╭–––╮");
            }
            System.out.println();
            System.out.print("  " + (i + 1) + "  ");
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
                System.out.format("| %s |", fields[i][j]);
            }

            System.out.println();
            System.out.print("     ");
            for (int j = 0; j < DIMENSIONS_OF_BOARD; j++) {
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
                """, hole1);
    }
}
