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
    private final int MAP_HEIGHT = 11;
    private final int MAP_WIDTH = 13;
    private final int PLAYER_ID = 1;
    private final int BOX_ID = 2;

    private boolean isRunning = true;
    private String winnerPlayerName;

    public GameSession(List<ClientHandler> playersInSession, String sessionHash) {
        this.playersInSession = playersInSession;
        this.sessionHash = sessionHash;

        for (ClientHandler chOut : playersInSession) {
            chOut.SetListener(this); // create session for all (add listener)
        }
    }

    @Override
    public void run() {
        System.out.println("Starting game session: " + sessionHash);
        while (this.isRunning);
    }

    private void Broadcast(Message message) {
        final String rawOutput = message.ConstructStringFromMessage();
        for (ClientHandler player : playersInSession) {
            player.sendMessage(rawOutput);
        }
    }

    @Override
    public synchronized void onPlayerMoves(final int finalX, final int finalY,
                                           final int deltaX, final int deltaY,
                                           final String playerName) {
        // IF IN PAYLOAD THERE IS A "RECONCILIATION"/"STEPBACK" -> client with that playerName steps back

        for (ClientHandler player : playersInSession) {
            if (finalX == player.GetPlayerX() && finalY == player.GetPlayerY() && player.GetIsAlive()) {
                // Someone is there already.
                // TODO: Reconcile

                System.out.println("Player already at" + finalX + ", " + finalY);
                return;
            }
        }
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

    @Override
    public void onPlayerDeath(String playerDiedName, String killerName) {
        // TODO: add score

        // TODO: if playerDiedName == kilerName don't increase score

        // TODO: Iterate through client handlers. If all !GetIsAlive() || only one is GetIsAlive()
        //  -> send message game over
        int playersAlive = 0;
        for (ClientHandler player : this.playersInSession) {
            if (player.GetIsAlive()) {
                playersAlive++;
            }
        }
        if (playersAlive <= 1) {
            // If player destroys someone and himself it's ok, he gets the points
            this.winnerPlayerName = killerName;
            try {
                Database database = new Database();
                database.updatePlayer(this.winnerPlayerName);
                database.close();
            } finally {
                final Message message = new Message(MessageType.MSG_SERVER_SESSION_END, this.winnerPlayerName);
                this.Broadcast(message);
                // Notice the server of the winner
                this.isRunning = false;
            }
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
    public synchronized void onPlayerSpawnBomb(final int x, final int y, final String playerName) {
        System.out.println("Player" + playerName + " spawns bomb at: " + x + ", " + y);

        String payload = "";
        payload += x;
        payload += " ";
        payload += y;
        payload += " ";
        payload += playerName;

        final Message msg = new Message(MessageType.MSG_SERVER_SENDS_PLAYER_PLANTS_BOMB, payload);
        this.Broadcast(msg);
    }

    @Override
    public synchronized void onPlayerSpawn() {
        // TODO: First thing that happens when player connect

    }

    @Override
    public synchronized void onPlayerReady(String playerName) throws InterruptedException {
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
                    chOut.GetOutStream().println(rawMessage); // send to client
                }
                return;
            }
        }
    }
}
