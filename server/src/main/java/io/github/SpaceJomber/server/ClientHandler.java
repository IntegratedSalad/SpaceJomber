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
                        break;
                    }

                    case MSG_USER_JOINED_LOBBY: {
                        final String lobbyHash = messageIn.GetPayload();
                        System.out.println("Client " + this.socket.getInetAddress() + "tries to join lobby: " + lobbyHash);

                        Lobby lobby = this.server.getLobbyManager().GetLobby(lobbyHash);
                        if (lobby != null) {
                            System.out.println("Client " + this.socket.getInetAddress() + "joined lobby: " + lobbyHash);
                        } else {
                            System.out.println("No such lobby as: " + lobbyHash);
                        }

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
