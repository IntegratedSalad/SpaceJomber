package io.github.SpaceJomber.systems;

import io.github.SpaceJomber.entities.Bomb;

// TODO: This interface can be abstracted to "RenderablePlacementListener"
public interface BombPlacementListener {
    void onBombPlaced(final int x, final int y, Bomb bomb);
    void onBombDetonate(final int x, final int y, Bomb bomb);
}
