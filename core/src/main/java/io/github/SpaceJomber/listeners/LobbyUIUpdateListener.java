package io.github.SpaceJomber.listeners;

public interface LobbyUIUpdateListener {

    void onLobbyIDReceived(final String lobbyID);
    void onLobbyPlayerJoined(final String playerName);
    void onLobbyPlayerLeft(final String playerName);
}
