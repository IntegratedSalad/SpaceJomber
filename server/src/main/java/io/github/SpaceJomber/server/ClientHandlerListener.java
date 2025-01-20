package io.github.SpaceJomber.server;

public interface ClientHandlerListener {
    void onPlayerMove(final int finalX, final int finalY,
                      final int deltaX, final int deltaY,
                      final String playerName);
    void onPlayerDeath();
    void onPlayerSpawnBomb();
    void onPlayerSpawn();
    void onPlayerReady(final String playerName) throws InterruptedException;
}
