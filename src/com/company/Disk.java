package com.company;

public class Disk {
    private Color color;

    public Disk(Color color) {
        this.color = color;
    }

    public String getColorString() {
        return color == Color.BLACK ? "B" : "W";
    }

    public Color getColor() {
        return color;
    }

    public void changeColor() {
        color = color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }
}
