package io.github.SpaceJomber.systems;

import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.listeners.SoundPlayerListener;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;

public class MessageInputSystem extends InputSystem {

    MultiplayerClient multiplayerClient;
    public MessageInputSystem(Player controlledPlayer, MultiplayerClient multiplayerClient, SoundPlayerListener spl) {
        super(controlledPlayer, spl);
        this.multiplayerClient = multiplayerClient;
    }

    @Override
    public boolean OnMoveUp() {
        if (!super.OnMoveUp()) return false;
        Gdx.app.log("MessageInputSystem, keyDown", "W...");
        final Message message = new Message(MessageType.MSG_USER_MAKES_ACTION, "0 1");
        this.multiplayerClient.SendMessage(message);
        return true;
    }

    @Override
    public boolean OnMoveDown() {
        if (!super.OnMoveDown()) return false;
        Gdx.app.log("MessageInputSystem, keyDown", "S...");
        final Message message = new Message(MessageType.MSG_USER_MAKES_ACTION, "0 -1");
        this.multiplayerClient.SendMessage(message);
        return true;
    }

    @Override
    public boolean OnMoveLeft() {
        if (!super.OnMoveLeft()) return false;
        Gdx.app.log("MessageInputSystem, keyDown", "A...");
        final Message message = new Message(MessageType.MSG_USER_MAKES_ACTION, "-1 0");
        this.multiplayerClient.SendMessage(message);
        return true;
    }

    @Override
    public boolean OnMoveRight() {
        if (!super.OnMoveRight()) return false;
        Gdx.app.log("MessageInputSystem, keyDown", "D...");
        final Message message = new Message(MessageType.MSG_USER_MAKES_ACTION, "1 0");
        this.multiplayerClient.SendMessage(message);
        return true;
    }

    @Override
    public boolean OnActionKey() {
        if (!super.OnActionKey()) return false;
        Gdx.app.log("MessageInputSystem, keyDown", "SPACE...");
        final Message message = new Message(MessageType.MSG_USER_MAKES_ACTION, "PLANT NULL");
        this.multiplayerClient.SendMessage(message);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }
}
