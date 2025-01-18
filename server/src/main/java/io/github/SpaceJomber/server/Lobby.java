package io.github.SpaceJomber.server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class Lobby {

    private final String lobbyHash;
    private List<ClientHandler> players = new ArrayList<>();
    private final int maxPlayers = 4;
    private boolean gameStarted = false;

    public Lobby(String lobbyHash) {
        this.lobbyHash = lobbyHash;
    }

    public String GetLobbyHash() {
        return this.lobbyHash;
    }

    public synchronized boolean AddPlayer(ClientHandler handler) {
        if (this.players.size() < this.maxPlayers && !this.gameStarted) {
            this.players.add(handler);
            return true;
        }
        return false;
    }

    public synchronized boolean RemovePlayer(ClientHandler handler) {
        return this.players.remove(handler);
    }

    public synchronized List<ClientHandler> GetPlayers() {
        return this.players;
    }

    public synchronized boolean AllPlayersReady() {
        return !this.players.isEmpty() && players.stream().allMatch(ClientHandler::isReady);
    }

    public synchronized int GetPlayerCount() {
        return this.players.size();
    }

    public synchronized void StartGame(ExecutorService sessionThreadPool) {
        this.gameStarted = true;

        GameSession gameSession = new GameSession(this.players, this.lobbyHash);
        sessionThreadPool.execute(gameSession);
    }
}
