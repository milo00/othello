package com.company;

public class Server {
    private final Logic logic;

    public Server(Color color) {
        this.logic = new Logic(color);
    }

    public void makeMove(Player player) {
        logic.print();
        int[] position = player.move();
        logic.makeMove(player, position[0], position[1]);
        logic.print();
    }

    public boolean ifEnded() {
        return logic.ifEnded();
    }
}
