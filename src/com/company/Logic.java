package com.company;

public class Logic {
    private final Board board;

    public Logic(Color color) {
        this.board = new Board(color);
    }

    //TODO: another idea
    public boolean makeMove(Player player, int x, int y) {
        Disk disk = board.takeDiskFromHole(player.getColor());
        boolean success = board.put(x, y, disk);
        if (!success) board.pushDiskBackToHole(disk);
        return success;
    }

    public boolean ifEnded() {
        return board.getHoleDisks(Color.BLACK) == 0 && board.getHoleDisks(Color.WHITE) == 0;
    }

    public void print() {
        board.print();
    }

    public Color getPlayerColor(int player) {
        return board.getPlayerColor(player);
    }

}
