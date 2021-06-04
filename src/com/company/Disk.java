package com.company;

public class Disk {
    private Color color;

    public Disk(Color color) {
        this.color = color;
    }

    public String getColor() {
        return color == Color.BLACK ? "B" : "W";
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
