package io.github.SpaceJomber.server;

import java.util.List;

/*
* Game Session is a session containing up to 4 players
* playing concurrently.
* */
public class GameSession implements Runnable {
    private final List<ClientHandler> players;
    private final String sessionHash;

    public GameSession(List<ClientHandler> players, String sessionHash) {
        this.players = players;
        this.sessionHash = sessionHash;
    }

    @Override
    public void run() {
        System.out.println("Starting game session: " + sessionHash);
        broadcast("Game session " + sessionHash + " is starting!");

        // Game logic here
        for (ClientHandler player : players) {
//            new Thread(player).start(); // <- Wrong!
        }

        // Wait for game to end (optional synchronization here)
        try {
            Thread.sleep(60000); // Placeholder for actual game logic duration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game session " + sessionHash + " ended.");
    }

    private void broadcast(String message) {
        for (ClientHandler player : players) {
            player.sendMessage(message);
        }
    }
}
