package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.SpaceJomber.systems.BombPlacementListener;
import io.github.SpaceJomber.systems.Renderable;
import io.github.SpaceJomber.utils.MapUtils;

public class Player implements Renderable {

    // TODO: Instantiate the multiplayerComponent as needed
    private Sprite sprite;
    private int x;
    private int y;
    private String name;
    private ENTITYID eid;

    private TiledMap tmRef;
    private BombPlacementListener bombPlacementListener;

    public Player(Sprite sprite, int x, int y, String name, ENTITYID eid, BombPlacementListener bombPlacementListener) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
        this.name = name;
        this.eid = eid;
        this.bombPlacementListener = bombPlacementListener;
    }

    public void Move(final int x, final int y) {
        // TODO: MapUtils.GetCellIdAtXY accepts TiledMap not layer
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

    public void PlantBomb(Sprite sprite, final String name, ENTITYID beid) {
        Bomb bomb = new Bomb(sprite,
            name,
            this.x,
            this.y,
            beid);
        bomb.PlantBomb(2000);
        this.bombPlacementListener.onBombPlaced(this.x, this.y, bomb);
    }

    public void SetMapRf(TiledMap tmRef) {
        this.tmRef = tmRef;
    }

    public ENTITYID GetEntityID() {
        return this.eid;
    }

    public ENTITYID GetBombID() {
        switch (this.eid) {
            case PLAYER_RED: {
                return ENTITYID.BOMB_RED;
            }
            case PLAYER_BLUE: {
                return ENTITYID.BOMB_BLUE;
            }
            case PLAYER_GREEN: {
                return ENTITYID.BOMB_GREEN;
            }
            case PLAYER_BLACK: {
                return ENTITYID.BOMB_BLACK;
            }
            default:
                return null;
        }
    }

    @Override
    public void render(SpriteBatch sbatch) {
        Gdx.app.log("Player render", "Sprite X: " + this.sprite.getX() + ", Y: " + this.sprite.getY());
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
    }
}
