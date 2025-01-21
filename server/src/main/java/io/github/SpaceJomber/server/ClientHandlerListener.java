package io.github.SpaceJomber.server;

public interface ClientHandlerListener {
    void onPlayerMoves(final int finalX, final int finalY,
                       final int deltaX, final int deltaY,
                       final String playerName);
    void onPlayerDeath(final String playerDiedName, final String killerName);
    void onPlayerSpawnBomb(final int x, final int y, final String playerName);
    void onPlayerSpawn();
    void onPlayerReady(final String playerName) throws InterruptedException;
}
