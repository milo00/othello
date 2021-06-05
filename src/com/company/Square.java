package com.company;

public class Square {
    private Disk disk;

    public Square() {
        disk = null;
    }

    public Color getColor() {
        return disk == null ? Color.GREEN : disk.getColor();
    }

    public boolean put(Disk disk) {
        if (this.disk == null) {
            this.disk = disk;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return disk == null ? " " : disk.getColorString();
    }
}
