package com.company;

public class Computer extends Player {
    private final Simulator simulator;

    public Computer(Color color) {
        super(color);
        simulator = new Simulator();
    }

    @Override
    public Position move(int id) {
        return simulator.simulate(this);
    }
}
