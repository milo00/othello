package com.company;

public class Computer extends Player {
    private final Simulator simulator;

    public Computer(Color color) {
        super(color);
        simulator = new Simulator();
    }

    //TODO: think if simulator should have player or color
    @Override
    public int[] move(int id) {
        int[] move = simulator.simulate(this);
        System.out.println("Computer move from position: " + (move[0] + 1) + " " + (move[1] + 1) );
        return move;
    }
}
