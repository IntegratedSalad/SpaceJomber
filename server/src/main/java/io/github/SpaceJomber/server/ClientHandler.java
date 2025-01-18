package io.github.SpaceJomber.server;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final GameServer server;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isReady = false;
    private Lobby playerLobby;
    private String playerColor;
    private ClientHandlerListener clientHandlerListener;

    // There should be a small class keeping all the player related data, but we can keep it all here
    private String playerName;
    private int playerX = 0;
    private int playerY = 0;

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

    public int GetPlayerX() {
        return this.playerX;
    }

    public int GetPlayerY() {
        return this.playerY;
    }

    public void SetPlayerX(int x) {
        this.playerX = x;
    }

    public void SetPlayerY(int y) {
        this.playerY = y;
    }

    public String GetPlayerName() {
        return this.playerName;
    }

    public String GetPlayerColor() {
        return this.playerColor;
    }

    public void SetListener(GameSession session) {
        this.clientHandlerListener = session;
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
                    case MSG_USER_READY: {
                        // When user clicks ready
                        // This clause handles this client's message.
                        // So this is called once
                        final String[] isPlayerReadyStr = messageIn.GetPayload().split(" ");
                        System.out.println("Received MSG_USER_READY: " + isPlayerReadyStr[0]);

                        System.out.println("This: " + this);

                        // TODO: Set color?
                        if (isPlayerReadyStr[0].equals("TRUE")) {
                            this.isReady = true;
                        } else {
                            this.isReady = false;
                        }
                        this.playerColor = isPlayerReadyStr[1];

                        int readyPlayers = 0;
                        final int allPlayers = this.playerLobby.GetPlayers().size();
                        for (final ClientHandler p : this.playerLobby.GetPlayers()) {
                            if (p.isReady) {
                                readyPlayers++;
                            }
                        }
                        if (readyPlayers == allPlayers) {
                            // All are ready, start session.
                            // This means that the last player clicked "Ready"

                            // BUG -> ONLY THE PERSON THAT CLICKS "READY" LAST GETS THEIR
                            // POSITION ASSIGNED

                            System.out.println("All are ready!");

                            // BUG HOST DOESN'T RECEIVE THEIR POSITION

                            List<int[]> posList = new ArrayList<>();
                            posList.add(new int[]{1, 1});
                            posList.add(new int[]{13, 1});
                            posList.add(new int[]{1, 11});
                            posList.add(new int[]{13, 11});

                            System.out.println("Lobby size:" + this.playerLobby.GetPlayers().size());
                            System.out.println("ReadyPlayers: ");
                            for (ClientHandler p : this.playerLobby.GetPlayers()) {
                                System.out.println(p);
                            }

                            for (int i = 0; i < this.playerLobby.GetPlayers().size(); i++) {
                                String payload = "";
                                payload += String.valueOf(posList.get(i)[0]);
                                payload += " ";
                                payload += String.valueOf(posList.get(i)[1]);
                                System.out.println("Payload of start session: " + payload);

                                this.playerLobby.GetPlayers().get(i).SetPlayerX(posList.get(i)[0]);
                                this.playerLobby.GetPlayers().get(i).SetPlayerY(posList.get(i)[1]);
//
                                System.out.println("For clientHandler:" + this.playerLobby.GetPlayers().get(i));
                                System.out.println("I am " + this.playerLobby.GetPlayers().get(i).GetPlayerName()
                                    + "and I received X: " + this.playerLobby.GetPlayers().get(i).playerX + " Y: " +
                                    this.playerLobby.GetPlayers().get(i).playerY);

                                messageOut = new Message(MessageType.MSG_SERVER_STARTS_SESSION, payload);
                                final String rawMessage = messageOut.ConstructStringFromMessage();

                                this.playerLobby.GetPlayers().get(i).GetOutStream().println(rawMessage);
                            }

                            this.server.StartSession(this.playerLobby.GetPlayers(), this.playerLobby.GetLobbyHash());

                            // TODO: When starting session, assign to each player a unique ID.
                            // This id will help assign position to a desired player.
                            // Usually, this needs to be on the server (or lobby) side, NOT ON ONE CLIENTHANDLER SIDE!!!
                        }
                        break;
                    }
                    case MSG_USER_GAMESCREEN_READY: {
                        // Send player names
                        String payload = "";
                        for (ClientHandler p : this.playerLobby.GetPlayers()) {
                            final String name = p.GetPlayerName();
                            final String color = p.GetPlayerColor();
                            payload += name;
                            payload += " ";
                            payload += color;
                            payload += " ";
                        }
                        System.out.println("Sending " + this.playerName +
                            " all player names: " + this.playerLobby.GetPlayers().size());
                        messageOut = new Message(MessageType.MSG_SERVER_SENDS_PLAYER_NAMES, payload);
                        final String rawMessage = messageOut.ConstructStringFromMessage();
                        this.out.println(rawMessage);

                        Thread.sleep(200);
                        this.clientHandlerListener.onPlayerReady(this.playerName);
                        break;
                    }
                    default: {
                        System.out.println("Server received an unknown message: " + rawInput);
                        break;
                    }
                }
            }
            // Handle game communication (after ready state)
        } catch (IOException | InterruptedException e) {
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
