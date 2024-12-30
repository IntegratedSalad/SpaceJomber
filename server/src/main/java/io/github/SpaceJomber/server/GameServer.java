package io.github.SpaceJomber.server;

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
* */
public class GameServer {
    private final int port;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);
    private final int maxConnectedClients = 4;

    private List<ClientHandler> currentLobby = new ArrayList<>();
    private int sessionCounter = 0;

    public GameServer(int port) {
        this.port = port;
    }

    public void Start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Starting server on port " + this.port);
            int connectedPlayers = 0;

            List<ClientHandler> currentSessionPlayers = new ArrayList<>();
            while (true) {
                if (connectedPlayers < this.maxConnectedClients) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected " + clientSocket.getInetAddress());

                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    currentSessionPlayers.add(clientHandler);
                }
            }
        }
    }

    public synchronized void checkLobbyReadyState() {
        boolean allReady = currentLobby.stream().allMatch(ClientHandler::isReady);
        if (allReady) {
            startGameSession();
        }
    }

    private void startGameSession() {
        System.out.println("Starting game session with " + currentLobby.size() + " players.");
        GameSession gameSession = new GameSession(new ArrayList<>(currentLobby), ++sessionCounter);
        threadPool.execute(gameSession);
        currentLobby.clear(); // Reset lobby for new players
    }
}
