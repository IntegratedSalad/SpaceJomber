package io.github.SpaceJomber.listeners;

public interface LobbyUIUpdateListener {

    void onLobbyIDReceived(final String lobbyID);
    void onLobbyPlayerJoined(final String playerName);
    void onSessionStarted();
    void onLobbyPlayerLeft(final String playerName);
}
