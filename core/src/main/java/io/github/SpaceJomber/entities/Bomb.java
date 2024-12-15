package io.github.SpaceJomber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Timer;
import io.github.SpaceJomber.systems.BombPlacementListener;
import io.github.SpaceJomber.systems.Renderable;

import java.util.List;

public class Bomb implements Renderable {

    private Sprite sprite;
    private String name;
    private int x;
    private int y;
    private ENTITYID eid;

    private TiledMap tmRef;
    private BombPlacementListener bombPlacementListener;

    public static Sprite blueBombSprite;
    public static Sprite greenBombSprite;
    public static Sprite redBombSprite;
    public static Sprite blackBombSprite;

    public Bomb(Sprite sprite, String name, int x, int y, ENTITYID eid, BombPlacementListener bombPlacementListener) {
        this.sprite = sprite;
        this.name = name; // to identify and remove from list
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
        this.eid = eid;
        this.bombPlacementListener = bombPlacementListener;
    }

    public void SetMapRf(TiledMap tmRef) {
        this.tmRef = tmRef;
    }

    public void PlantBomb(final int delayS) {
        // TODO: Place bomb -> method in MapUtils? (New renderable)
        // TODO: Maybe MapSystem, where access to the map can be multithreaded and guarded by locks!
        // Start timer
        this.scheduleTask(delayS);

//        if (tmRef != null) {
//            Cell cell = new Cell();
//            cell.setTile()
//        }
    }

    private void Explode() {
    }

    private Timer.Task scheduleTask(int delayS) {
        BombPlacementListener bl = this.bombPlacementListener;
        final int x = this.x;
        final int y = this.y;
        final Bomb b = this;
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log("Bomb schedule Task", "Bomb detonating at " + x + "," + y);
                bl.onBombDetonate(x, y, b);
            }
        };
        Timer.instance().scheduleTask(task, delayS);
        return task;
    }

    public static void intializeBombSpriteList(final Sprite r,
                                        final Sprite g,
                                        final Sprite blue,
                                        final Sprite black) {
        redBombSprite = r;
        greenBombSprite = g;
        blueBombSprite = blue;
        blackBombSprite = black;
    }

    public static String GetBombNameFromID(final ENTITYID eid) {
        switch (eid) {
            case BOMB_RED: {
                return "Red Bomb";
            }
            case BOMB_BLACK: {
                return "Black Bomb";
            }
            case BOMB_BLUE: {
                return "Blue Bomb";
            }
            case BOMB_GREEN: {
                return "Green Bomb";
            } default: {
                return null;
            }
        }
    }

    @Override
    public void render(SpriteBatch sbatch) {
//        Gdx.app.log("Bomb render", "Sprite X: " + this.sprite.getX() + ", Y: " + this.sprite.getY());
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
    }

    @Override
    public ENTITYID GetEntityID() {
        return this.eid;
    }
}
