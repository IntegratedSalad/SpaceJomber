package io.github.SpaceJomber.systems;
import io.github.SpaceJomber.entities.BombFire;
import io.github.SpaceJomber.entities.FireElement;

public interface FirePlacementListener {
//    void onFirePlaced(final int x, final int y, BombFire bf);
    void onFireExtinguish(final int x, final int y, FireElement fe);
}
