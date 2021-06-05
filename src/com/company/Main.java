package com.company;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        Player player = new Player(Color.BLACK);
        server.play();
    }
}
