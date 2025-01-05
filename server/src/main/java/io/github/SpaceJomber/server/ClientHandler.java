package io.github.SpaceJomber.server;
import io.github.SpaceJomber.shared.Message;

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
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            Message message;
            String rawInput;
            while ((rawInput = in.readLine()) != null) {
                System.out.println("Received from client raw input: " + rawInput);
                message = new Message(rawInput);

                switch (message.GetType()) {
                    case MSG_USER_LOGGED_IN: {
                        break;
                    }

                    case MSG_USER_CREATED_LOBBY: {
                        final String lobbyHash = this.server.getLobbyManager().CreateLobby();
                        System.out.println("Client " + this.socket.getInetAddress() + "created lobby " + lobbyHash);
                        break;
                    }

                    case MSG_USER_JOINED_LOBBY: {
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
