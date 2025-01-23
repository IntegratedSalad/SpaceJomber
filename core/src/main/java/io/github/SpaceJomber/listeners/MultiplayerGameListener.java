package io.github.SpaceJomber.listeners;

public interface MultiplayerGameListener {

    // Remember to ALWAYS implement these methods runnning Gdx.post.Runnable!!!
    void onPlayerMove(final String playerName, final int newPositionX, final int newPositionY);
    void onPlayerReceiveNames(final String[] payload);
    void onPlayerPlantsBomb(final String playerName, final int posX, final int posY);
    void onPlayerDiesIncomingMessage(final String playerName);
    void onSessionEnd();
}
