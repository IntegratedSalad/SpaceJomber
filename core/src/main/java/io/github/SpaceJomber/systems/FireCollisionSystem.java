package io.github.SpaceJomber.systems;
import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.entities.FireElement;
import io.github.SpaceJomber.entities.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FireCollisionSystem {
    private Queue<Renderable> flameQueue;
    private List<Player> playerList;

    public FireCollisionSystem(Queue<Renderable> flameQueue) {
        this.playerList = new ArrayList<>();
        this.flameQueue = flameQueue;
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
    }
    public void checkCollisions() {
        for (Player player : this.playerList) {
            for (Renderable flame : flameQueue) {
                if (flame instanceof FireElement) {
                    FireElement fireElement = (FireElement) flame;

                    // Check for collision
                    if (player.GetX() == fireElement.GetX() && player.GetY() == fireElement.GetY()) {
                        player.Die(); // Handle player death
                        Gdx.app.log("FireCollisionSystem", "Player collided with fire at: " +
                            fireElement.GetX() + ", " + fireElement.GetY());
                    }
                }
            }
        }
    }
}
