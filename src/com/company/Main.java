package com.company;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(Color.BLACK);
        Player player = new Player(Color.BLACK);
        server.makeMove(player);
    }
}
