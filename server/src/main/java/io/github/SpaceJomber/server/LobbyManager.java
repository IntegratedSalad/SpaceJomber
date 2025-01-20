package io.github.SpaceJomber.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {

    private final Map<String, Lobby> lobbyConcurrentHashMap = new ConcurrentHashMap<>();

    public String CreateLobby() {
        String lobbyHash = this.GenerateLobbyHash();
        Lobby lobby = new Lobby(lobbyHash);
        this.lobbyConcurrentHashMap.put(lobbyHash, lobby);
        return lobbyHash;
    }

    public Lobby GetLobby(String lobbyHash) {
        return this.lobbyConcurrentHashMap.get(lobbyHash);
    }

    private String GenerateLobbyHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
