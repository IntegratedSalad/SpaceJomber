package io.github.SpaceJomber.listeners;

import io.github.SpaceJomber.entities.Bomb;

public interface BombPlacementListener {
    void onBombPlaced(final int x, final int y, Bomb bomb);
    void onBombDetonate(final int x, final int y, Bomb bomb);
}
