package io.github.SpaceJomber.systems;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.SpaceJomber.entities.Bomb;
import io.github.SpaceJomber.entities.ENTITYID;
import io.github.SpaceJomber.entities.Player;

import java.awt.event.KeyEvent;

public class InputSystem implements InputProcessor {

    private Player controlledPlayer;
    public InputSystem(Player controlledPlayer) {
        this.controlledPlayer = controlledPlayer;
    }

    public boolean OnMoveUp() {
        Gdx.app.log("InputSystem, keyDown", "W...");
        return this.controlledPlayer.Move(0, 1);
    }

    public boolean OnMoveDown() {
        Gdx.app.log("InputSystem, keyDown", "S...");
        return this.controlledPlayer.Move(0, -1);
    }

    public boolean OnMoveLeft() {
        Gdx.app.log("InputSystem, keyDown", "A...");
        return this.controlledPlayer.Move(-1, 0);
    }

    public boolean OnMoveRight() {
        Gdx.app.log("InputSystem, keyDown", "D...");
        return this.controlledPlayer.Move(1, 0);
    }

    public boolean OnActionKey() { // default: space
        // TODO: Only one bomb at a time
        Sprite bombSprite = null;
        final ENTITYID beid = this.controlledPlayer.GetBombID();
        if (beid != null) {
            switch (beid) {
                case BOMB_RED: {
                    bombSprite = Bomb.redBombSprite;
                }
                case BOMB_BLACK: {
                    bombSprite = Bomb.blackBombSprite;
                }
                case BOMB_BLUE: {
                    bombSprite = Bomb.blueBombSprite;
                }
                case BOMB_GREEN: {
                    bombSprite = Bomb.greenBombSprite;
                } default: {
                    Gdx.app.debug("InputSystem, OnActionKey", "Bomb id null!");
                    return false;
                }
            }
        }
        bombSprite = new Sprite(bombSprite);
        this.controlledPlayer.PlantBomb(bombSprite, Bomb.GetBombNameFromID(beid), beid);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (keycode == Input.Keys.SPACE) {
            return this.OnActionKey();
        } else if (keycode == Input.Keys.A) {
            return this.OnMoveLeft();
        } else if (keycode == Input.Keys.D) {
            return this.OnMoveRight();
        } else if (keycode == Input.Keys.W) {
            return  this.OnMoveUp();
        } else if (keycode == Input.Keys.S) {
            return this.OnMoveDown();
        }
        return false;
    }

    // When it comes to multiplayer, do we want to move the player here, or send a message,
    // then wait for the confirmation from the server? Server replies -> this.controlledPlayer.Move is called?
    // Maybe when it comes to the multiplayer session, nothing actually happens client side without confirmation
    // from the server?
    // Everything is just sending messages?

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
