package com.company;

import java.util.Random;

public class Computer extends Player {
    public Computer(Color color) {
        super(color);
    }

    @Override
    public int[] move(int id) {
        Random random = new Random();
        return new int[]{random.nextInt(8), random.nextInt(8)};
    }
}
