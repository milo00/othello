package com.company;

import java.util.ArrayList;
import java.util.Stack;

//TODO: rename
public class Hole {
    private static final int INITIAL_NUMBER_OF_DISKS = 32;
    private final Stack<Disk> disks;
    private final Color color;

    public Hole(Color color) {
        this.color = color;
        this.disks = new Stack<>();
        for (int i = 0; i < INITIAL_NUMBER_OF_DISKS; i++) {
            disks.add(new Disk(color));
        }
    }

    public int getDisks() {
        return disks.size();
    }

    public Disk takeDisk() {
        return disks.pop();
    }

    public void pushDisk(Disk disk) {
        disks.push(disk);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return Integer.toString(disks.size());
    }
}
