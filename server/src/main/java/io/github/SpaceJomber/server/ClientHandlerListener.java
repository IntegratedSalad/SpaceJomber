package io.github.SpaceJomber.server;

public interface ClientHandlerListener {
    void onPlayerMove();
    void onPlayerDeath();
    void onPlayerSpawnBomb();
    void onPlayerSpawn();
}
