package com.company;

public class Square {
    private Disk disk;

    public Square() {
        disk = null;
    }

    @Override
    public String toString() {
        return disk == null ? " " : disk.getColor();
    }
}
