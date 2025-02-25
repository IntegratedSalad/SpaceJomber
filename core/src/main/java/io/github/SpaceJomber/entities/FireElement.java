package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Timer;
import io.github.SpaceJomber.listeners.FirePlacementListener;
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

    private final int timeToExtinguish = 5;

    private String planterName;

    public FireElement(String name,
                       Sprite sprite,
                       int x, int y,
                       ENTITYID eid, TiledMap tmRef, FirePlacementListener firePlacementListener) {
        this.name = name;
        this.sprite = new Sprite(sprite);
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
        this.eid = eid;
        this.tmRef = tmRef;
        this.firePlacementListener = firePlacementListener;
        this.scheduleTask(1);
    }

    public void SetPlanterName(final String planterName) {
        this.planterName = planterName;
    }

    public String GetPlanterName() {
        return this.planterName;
    }

    public void SetDestroysTile(final boolean b) {
        this.destroysTile = b;
    }

    public boolean GetDestroysTile() {
        return this.destroysTile;
    }

    public int GetX() {
        return this.x;
    }

    public int GetY() {
        return this.y;
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
        Timer.instance().scheduleTask(task, delayS); // TODO: Maybe convert this to float
        return task;
    }

    @Override
    public void render(SpriteBatch sbatch) {
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
        this.sprite.getTexture().dispose(); // ?
    }

    @Override
    public ENTITYID GetEntityID() {
        return null;
    }
}
