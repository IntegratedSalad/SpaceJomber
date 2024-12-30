package io.github.SpaceJomber.server;

import java.util.List;

/*
* Game Session is a session containing up to 4 players
* playing concurrently.
* */
public class GameSession implements Runnable {
    private final List<ClientHandler> players;
    private final int sessionId;

    public GameSession(List<ClientHandler> players, int sessionId) {
        this.players = players;
        this.sessionId = sessionId;
    }

    @Override
    public void run() {
        System.out.println("Starting game session: " + sessionId);
        broadcast("Game session " + sessionId + " is starting!");

        // Game logic here
        for (ClientHandler player : players) {
            new Thread(player).start(); // Handle each player's communication in its own thread
        }

        // Wait for game to end (optional synchronization here)
        try {
            Thread.sleep(60000); // Placeholder for actual game logic duration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game session " + sessionId + " ended.");
    }

    private void broadcast(String message) {
        for (ClientHandler player : players) {
            player.sendMessage(message);
        }
    }
}
