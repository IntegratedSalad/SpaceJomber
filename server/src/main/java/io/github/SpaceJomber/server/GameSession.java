package io.github.SpaceJomber.server;

import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

import java.util.List;

/*
* Game Session is a session containing up to 4 players
* playing concurrently.
* It is responsible for broadcasting each player action to the other players.
* */
public class GameSession implements Runnable, ClientHandlerListener {
    private final List<ClientHandler> playersInSession;
    private final String sessionHash;

    public GameSession(List<ClientHandler> playersInSession, String sessionHash) {
        this.playersInSession = playersInSession;
        this.sessionHash = sessionHash;

        // TODO: Send all clients all positions on message receive "MSG_USER_SENDS_GAMESCREEN_SETUP"
        for (ClientHandler chOut : playersInSession) {
            chOut.SetListener(this);
            for (ClientHandler chPos : playersInSession) {
                final int xpos = chPos.GetPlayerX();
                final int ypos = chPos.GetPlayerY();
                String payload = String.valueOf(xpos) + " " + String.valueOf(ypos) + " " + chPos.GetPlayerName();
                Message sendPosMsg = new Message(MessageType.MSG_TWOWAY_SENDS_POSITION, payload);
                final String rawMessage = sendPosMsg.ConstructStringFromMessage();
                chOut.GetOutStream().println(rawMessage); // send to client
            }
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
        for (ClientHandler player : playersInSession) {
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
        // TODO: First thing that happens when player connect

    }
}
