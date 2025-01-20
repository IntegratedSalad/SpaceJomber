package io.github.SpaceJomber.server;

import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
* Game Session is a session containing up to 4 players
* playing concurrently.
* It is responsible for broadcasting each player action to the other players.
* */
public class GameSession implements Runnable, ClientHandlerListener {
    private final List<ClientHandler> playersInSession;
    private final String sessionHash;
    private final int MAP_HEIGHT = 11;
    private final int MAP_WIDTH = 13;
    private final int PLAYER_ID = 1;
    private final int BOX_ID = 2;

    // TODO?: Add map 2D array
//    private ArrayList<ArrayList<String>> sessionMap;

    public GameSession(List<ClientHandler> playersInSession, String sessionHash) {
        this.playersInSession = playersInSession;
        this.sessionHash = sessionHash;
//        this.sessionMap = new ArrayList<>();

//        for (int i = 0; i < MAP_HEIGHT; i++) {
//            this.sessionMap.add(new ArrayList<>());
//            for (int j = 0; j < MAP_WIDTH; j++) {
//                this.sessionMap.get(i).add("NULL");
//            }
//        }
//
//        this.sessionMap.get(0).set(0, 3);

        // W: 13
        // H: 11

        // Players have their position set here.
        for (ClientHandler chOut : playersInSession) {
            chOut.SetListener(this); // create session for all (add listener)
        }
    }

    @Override
    public void run() {
        System.out.println("Starting game session: " + sessionHash);
//        broadcast("Game session " + sessionHash + " is starting!");
        // Wait for game to end (optional synchronization here)
        while (true) {
        }
    }

    private void Broadcast(Message message) {
        final String rawOutput = message.ConstructStringFromMessage();
        for (ClientHandler player : playersInSession) {
            player.sendMessage(rawOutput);
        }
    }

    @Override
    public synchronized void onPlayerMove(final int finalX, final int finalY,
                                          final int deltaX, final int deltaY,
                                          final String playerName) {
        // TODO: Broadcast message with payload
        // IF IN PAYLOAD THERE IS A "RECONCILIATION"/"STEPBACK" -> client with that playerName steps back

        for (ClientHandler player : playersInSession) {
            if (finalX == player.GetPlayerX() && finalY == player.GetPlayerY()) {
                // Someone is there already.
                // TODO: Reconcile

                System.out.println("Player already at" + finalX + ", " + finalY);
                return;
            }
        }
        // TODO: message to every client stating that playerName moved
        final String finalXString = String.valueOf(finalX);
        final String finalYString = String.valueOf(finalY);
        String payload = "";
        payload += finalXString;
        payload += " ";
        payload += finalYString;
        payload += " ";
        payload += playerName;
        final Message msg = new Message(MessageType.MSG_SERVER_SENDS_PLAYER_MOVED, payload);
        this.Broadcast(msg);
        System.out.println("Broadcast of " + playerName + " moved to " + finalXString + " " + finalYString);
        ClientHandler p = FindClientByName(playerName);
        if (p != null) {
            p.SetPlayerX(finalX);
            p.SetPlayerY(finalY);
        }
    }

    private ClientHandler FindClientByName(String playerName) {
        for (ClientHandler player : playersInSession) {
            if (player.GetPlayerName().equals(playerName)) {
                return player;
            }
        }
        return null;
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

    @Override
    public synchronized void onPlayerReady(String playerName) throws InterruptedException {
        // TODO: Send all clients all positions on message receive "MSG_USER_SENDS_GAMESCREEN_SETUP"

        System.out.println("onPlayerReady: " + playerName + " playersInSession: " + playersInSession.size());
        for (ClientHandler chOut : playersInSession) {
            if (chOut.GetPlayerName().equals(playerName)) {
                System.out.println("Sending " + chOut.GetPlayerName() + " all positions.");
                for (ClientHandler chPos : playersInSession) {
                    final int xpos = chPos.GetPlayerX();
                    final int ypos = chPos.GetPlayerY();
                    System.out.println("Sending " + chPos.GetPlayerName() + " at " + xpos + " " + ypos);
                    String payload = String.valueOf(xpos) + " " + String.valueOf(ypos) + " " + chPos.GetPlayerName();
                    Message sendPosMsg = new Message(MessageType.MSG_TWOWAY_SENDS_POSITION, payload);
                    final String rawMessage = sendPosMsg.ConstructStringFromMessage();

//                    Thread.sleep(200);
                    chOut.GetOutStream().println(rawMessage); // send to client
                }
                return;
            }
        }
    }
}
