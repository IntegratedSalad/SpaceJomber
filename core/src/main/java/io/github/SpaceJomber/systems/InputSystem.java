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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (keycode == Input.Keys.SPACE) {
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
        } else if (keycode == Input.Keys.A) {
            this.controlledPlayer.Move(-1, 0);
            Gdx.app.log("InputSystem, keyDown", "A...");
            return true;
        } else if (keycode == Input.Keys.D) {
            this.controlledPlayer.Move(1, 0);
            Gdx.app.log("InputSystem, keyDown", "D...");
            return true;
        } else if (keycode == Input.Keys.W) {
            this.controlledPlayer.Move(0, 1);
            Gdx.app.log("InputSystem, keyDown", "W...");
            return true;
        } else if (keycode == Input.Keys.S) {
            this.controlledPlayer.Move(0, -1);
            Gdx.app.log("InputSystem, keyDown", "S...");
            return true;
        }
        return false;
    }

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
