package io.github.SpaceJomber.server;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final GameServer server;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isReady = false;

    private String playerName;

    private Lobby playerLobby;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public PrintWriter GetOutStream() {
        return this.out;
    }

    @Override
    public void run() {
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);

            Message messageIn;
            Message messageOut;
            String rawInput;
            String rawOutput;
            while ((rawInput = in.readLine()) != null) {
                System.out.println("Received from client raw input: " + rawInput);
                messageIn = new Message(rawInput);

                switch (messageIn.GetType()) {
                    case MSG_USER_LOGGED_IN: {
                        break;
                    }
                    case MSG_USER_CREATED_LOBBY: {
                        final String lobbyHash = this.server.getLobbyManager().CreateLobby();
                        System.out.println("Client " + this.socket.getInetAddress() + "created lobby " + lobbyHash);

                        messageOut = new Message(MessageType.MSG_SERVER_SENDS_SESSION_ID, lobbyHash);
                        rawOutput = messageOut.ConstructStringFromMessage();

                        // Send Lobby UUID to client
                        System.out.println("Sending " + rawOutput + " to client " + this.socket.getInetAddress());
                        this.out.println(rawOutput);
                        this.server.getLobbyManager().GetLobby(lobbyHash).AddPlayer(this);
                        this.playerLobby = this.server.getLobbyManager().GetLobby(lobbyHash);
                        System.out.println("Player lobby: " + this.playerLobby);

                        break;
                    }

                    case MSG_USER_JOINED_LOBBY: {
                        final String lobbyHash = messageIn.GetPayload().split("\\|")[0]; // :lobbyHash|
                        this.playerName = messageIn.GetPayload().split("\\|")[1];
                        System.out.println("playername: " + this.playerName);
                        System.out.println("Lobbyhash" + lobbyHash);

                        System.out.println("Client " + this.socket.getInetAddress() + "tries to join lobby: " + lobbyHash);

                        Lobby lobby = this.server.getLobbyManager().GetLobby(lobbyHash);
                        this.playerLobby = lobby;
                        if (lobby != null) {
                            // TODO: Announce to client if lobby is full (>=4 players)
                            System.out.println("Client " + this.socket.getInetAddress() + "joined lobby: " + lobbyHash);
                            lobby.AddPlayer(this);
                            final int lobbySize = lobby.GetPlayerCount();
                            System.out.println("Lobby size: " + lobbySize);

                            // TODO: Send names of playersInLobby
                            System.out.println("Joined player name: " + this.playerName);
                            System.out.println("Sending broadcast to: " + this.playerLobby.GetPlayers().size());
                            for (ClientHandler p : this.playerLobby.GetPlayers()) {
                                final String pName = p.playerName;
                                messageOut = new Message(MessageType.MSG_TWOWAY_SEND_PLAYER_NAME, pName);
                                this.server.Broadcast(this.playerLobby.GetPlayers(), messageOut);
                            }

                        } else {
                            System.out.println("No such lobby as: " + lobbyHash);

                            // TODO: Announce to client that this lobby doesn't exist
                        }
                        break;
                    }

                    case MSG_TWOWAY_SEND_PLAYER_NAME: {
                        final String receivedPlayerName = messageIn.GetPayload();
                        System.out.println("Server received player name: " + receivedPlayerName);
                        this.playerName = receivedPlayerName;

                        // Broadcast message here, NOT ABOVE WHEN JOINING/CREATING (now we have playerName)
                        messageOut = new Message(MessageType.MSG_TWOWAY_SEND_PLAYER_NAME, this.playerName);
                        // Here, the player joining the lobby didn't have this instance
                        // But, because lobby can be accessed from the server instance, and there's only
                        // one server instance, we can get the exact same lobby for this client when he joins it
                        this.server.Broadcast(this.playerLobby.GetPlayers(), messageOut);
                        break;
                    }

                    default: {
                        System.out.println("Server received an unknown message: " + rawInput);
                        break;
                    }
                }
            }

            // Handle game communication (after ready state)
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Client disconnected: " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
