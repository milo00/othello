package com.company;

import java.util.ArrayList;

//TODO: rename
public class Hole {
    private static final int INITIAL_NUMBER_OF_DISKS = 32;
    private ArrayList<Disk> disks;
    private Color color;

    public Hole(Color color) {
        this.color = color;
        this.disks = new ArrayList<>(INITIAL_NUMBER_OF_DISKS);
        for (int i = 0; i < INITIAL_NUMBER_OF_DISKS; i++) {
            disks.add(new Disk(color));
        }
    }

    public ArrayList<Disk> getDisks() {
        return disks;
    }

    public void setDisks(ArrayList<Disk> disks) {
        this.disks = disks;
    }

    @Override
    public String toString() {
        return Integer.toString(disks.size());
    }
}
