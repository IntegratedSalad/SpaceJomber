package io.github.SpaceJomber.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {

    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();

    // Pass Executor

    public String CreateLobby() {
        String lobbyHash = this.GenerateLobbyHash();
        Lobby lobby = new Lobby(lobbyHash);
        this.lobbies.put(lobbyHash, lobby);
        return lobbyHash;
    }

    private String GenerateLobbyHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
