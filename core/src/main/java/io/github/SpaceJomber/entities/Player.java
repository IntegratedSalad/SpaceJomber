package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.SpaceJomber.systems.Renderable;
import io.github.SpaceJomber.utils.MapUtils;

public class Player implements Renderable {

    // TODO: Instantiate the multiplayerComponent as needed
    private Sprite sprite;
    private int x;
    private int y;
    private String name;

    // Maybe each instance gets a reference to TiledMap/TiledMapTileLayer?

    private TiledMap tmRef;

    public Player(Sprite sprite, int x, int y, String name) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
        this.tmRef = tmRef;
        this.name = name;
    }

    public void Move(final int x, final int y) {
        final int cellId = MapUtils.GetCellIdAtXY((TiledMapTileLayer)this.tmRef.getLayers().get(0),
            this.x + x,
            this.y + y);

        Gdx.app.log("Move", "CellId at moveto: " + cellId);
        if (this.tmRef != null) {
            if (cellId != 42) {
                return;
            }
        }
        this.sprite.setX(this.sprite.getX() + x);
        this.sprite.setY(this.sprite.getY() + y);
        this.x += x;
        this.y += y;
    }

    public void SetMapRf(TiledMap tmRef) {
        this.tmRef = tmRef;
    }

    @Override
    public void render(SpriteBatch sbatch) {
        Gdx.app.log("Player Position", "Sprite X: " + this.sprite.getX() + ", Y: " + this.sprite.getY());
//        this.sprite.setPosition(this.x, this.y);
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
    }
}
