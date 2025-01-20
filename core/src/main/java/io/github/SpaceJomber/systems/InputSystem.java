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

    public void OnMoveUp() {
        this.controlledPlayer.Move(0, 1);
        Gdx.app.log("InputSystem, keyDown", "W...");
    }

    public void OnMoveDown() {
        this.controlledPlayer.Move(0, -1);
        Gdx.app.log("InputSystem, keyDown", "S...");
    }

    public void OnMoveLeft() {
        this.controlledPlayer.Move(-1, 0);
        Gdx.app.log("InputSystem, keyDown", "A...");
    }

    public void OnMoveRight() {
        this.controlledPlayer.Move(1, 0);
        Gdx.app.log("InputSystem, keyDown", "D...");
    }

    public void OnActionKey() { // default: space
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
                }
            }
        }
        bombSprite = new Sprite(bombSprite);
        this.controlledPlayer.PlantBomb(bombSprite, Bomb.GetBombNameFromID(beid), beid);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (keycode == Input.Keys.SPACE) {
            this.OnActionKey();
            return true;
        } else if (keycode == Input.Keys.A) {
            this.OnMoveLeft();
            return true;
        } else if (keycode == Input.Keys.D) {
            this.OnMoveRight();
            return true;
        } else if (keycode == Input.Keys.W) {
            this.OnMoveUp();
            return true;
        } else if (keycode == Input.Keys.S) {
            this.OnMoveDown();
            return true;
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
