package com.company;

public abstract class Player {
    private final Color color;

    public Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

   public abstract int[] move(int id);
}
