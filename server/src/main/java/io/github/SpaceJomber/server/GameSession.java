package io.github.SpaceJomber.server;

import java.util.List;

/*
* Game Session is a session containing up to 4 players
* playing concurrently.
* It is responsible for broadcasting each player action to the other players.
* */
public class GameSession implements Runnable, ClientHandlerListener {
    private final List<ClientHandler> players;
    private final String sessionHash;

    public GameSession(List<ClientHandler> players, String sessionHash) {
        this.players = players;
        this.sessionHash = sessionHash;
        for (ClientHandler ch : players) {
            ch.SetListener(this);
        }
    }

    @Override
    public void run() {
        System.out.println("Starting game session: " + sessionHash);
//        broadcast("Game session " + sessionHash + " is starting!");
        // Wait for game to end (optional synchronization here)
        try {
            Thread.sleep(60000); // Placeholder for actual game logic duration
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game session " + sessionHash + " ended.");
    }

    private void Broadcast(String message) {
        for (ClientHandler player : players) {
            player.sendMessage(message);
        }
    }

    @Override
    public synchronized void onPlayerMove() {

    }

    @Override
    public synchronized void onPlayerDeath() {

    }

    @Override
    public synchronized void onPlayerSpawnBomb() {

    }

    @Override
    public synchronized void onPlayerSpawn() {

    }
}
