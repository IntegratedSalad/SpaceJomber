package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Timer;
import io.github.SpaceJomber.systems.BombPlacementListener;
import io.github.SpaceJomber.systems.FirePlacementListener;
import io.github.SpaceJomber.systems.Renderable;

public class FireElement implements Renderable {

    private Sprite sprite;
    private String name;
    private int x;
    private int y;
    private ENTITYID eid;

    private TiledMap tmRef;

    private boolean destroysTile = false;
    private FirePlacementListener firePlacementListener;

    public FireElement(String name, Sprite sprite, int x, int y, ENTITYID eid, TiledMap tmRef, FirePlacementListener firePlacementListener) {
        this.name = name;
        this.sprite = new Sprite(sprite);
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
        this.eid = eid;
        this.tmRef = tmRef;
        this.firePlacementListener = firePlacementListener;
    }

    public void SetDestroysTile(final boolean b) {
        this.destroysTile = b;
    }

    private Timer.Task scheduleTask(int delayS) {
        FirePlacementListener fl = this.firePlacementListener;
        final int x = this.x;
        final int y = this.y;
        final FireElement fe = this;
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log("Bomb schedule Task", "Fire dissipating at " + x + "," + y);
                fl.onFireExtinguish(x, y, fe);
            }
        };
        Timer.instance().scheduleTask(task, delayS);
        return task;
    }

    @Override
    public void render(SpriteBatch sbatch) {
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {

    }

    @Override
    public ENTITYID GetEntityID() {
        return null;
    }
}
