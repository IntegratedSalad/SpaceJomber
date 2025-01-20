package io.github.SpaceJomber.systems;

import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.networking.MultiplayerClient;

public class MessageInputSystem extends InputSystem {

    MultiplayerClient multiplayerClient;
    public MessageInputSystem(Player controlledPlayer, MultiplayerClient multiplayerClient) {
        super(controlledPlayer);
        this.multiplayerClient = multiplayerClient;
    }

    @Override
    public void OnMoveUp() {
        super.OnMoveUp();
        Gdx.app.log("MessageInputSystem, keyDown", "W...");
    }

    @Override
    public void OnMoveDown() {
        super.OnMoveDown();
        Gdx.app.log("MessageInputSystem, keyDown", "S...");
    }

    @Override
    public void OnMoveLeft() {
        super.OnMoveLeft();
        Gdx.app.log("MessageInputSystem, keyDown", "A...");
    }

    @Override
    public void OnMoveRight() {
        super.OnMoveRight();
        Gdx.app.log("MessageInputSystem, keyDown", "D...");
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}
