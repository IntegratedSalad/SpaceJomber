package io.github.SpaceJomber.server;

import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
*
* Game Server can serve N concurrent sessions, where each
* session contains up to four players.
* So this class manages all things concurrent/multiplayer
* */
public class GameServer {
    private final int port;
    private final ExecutorService clientThreadPool = Executors.newFixedThreadPool(64);
    private final ExecutorService sessionThreadPool = Executors.newFixedThreadPool(16);
    // Maximum of 16 sessions with four players

    private final int maxNumberOfSessions = 16;

    private List<ClientHandler> currentLobby = new ArrayList<>();
    private int sessionCounter = 0;

    private final LobbyManager lobbyManager = new LobbyManager();

    public GameServer(int port) {
        this.port = port;
    }

    public void StartSession(List<ClientHandler> players, String sessionHash) {
        GameSession newGs = new GameSession(players, sessionHash);
        this.sessionThreadPool.execute(newGs);
    }

    public void Start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Starting server on port " + this.port);
            int connectedPlayers = 0;

            List<ClientHandler> currentSessionPlayers = new ArrayList<>();

            while (true) {
                // This happens in lobby
                // Won't happen anywhere else, because when clicking
                // "New Multiplayer Game"
                // Client can create or join lobby.
                // If he creates a lobby, he generates a hash, that he has to send everyone.
                // If he wishes to join, he enters this hash.
                // Each lobby can hold only four players that press space to signal
                // that they are ready.
                // If all are ready, the game starts.

                Socket clientSocket = serverSocket.accept(); // blocks
                System.out.println("New connection from " + clientSocket.getInetAddress());
                connectedPlayers++;

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                this.clientThreadPool.execute(clientHandler); // assign a thread from thread pool

                // TODO: Listen for the "create lobby" and "join lobby" messages.

                // TODO: When user clicks "New Multiplayer Game", he connects to the server

            }
        }
    }

    public void Broadcast(final List<ClientHandler> clients, Message message) {
       final String rawOutput = message.ConstructStringFromMessage();
       for (ClientHandler clientHandler : clients) {
           clientHandler.GetOutStream().println(rawOutput);
       }
    }

    public LobbyManager getLobbyManager() {
        return this.lobbyManager;
    }
}
