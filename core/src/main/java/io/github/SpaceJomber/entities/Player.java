package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.SpaceJomber.listeners.BombPlacementListener;
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

    private int bombDelayInS = 2;
    private boolean bombPlanted = false;

    private boolean isAlive = true;

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
        final int cellId = MapUtils.GetCellIdAtXY(this.tmRef,
            this.x + x,
            this.y + y);

        Gdx.app.log("Move", "CellId at moveto: " + cellId);
        // TODO: If there's bomb in the way.
        if (this.tmRef != null) {
            if (cellId != MapUtils.TILEID_EMPTY_SPACE) {
                return;
            }
        }
        this.sprite.setX(this.sprite.getX() + x);
        this.sprite.setY(this.sprite.getY() + y);
        this.x += x;
        this.y += y;
    }

    // Only to set the player
    public void Teleport(final int x, final int y) {
        this.sprite.setX(x);
        this.sprite.setY(y);
        this.x = x;
        this.y = y;
    }

    public void SetBombPlanted(final boolean isPlanted) {
        this.bombPlanted = isPlanted;
    }

    public boolean GetBombPlanted() {
        return this.bombPlanted;
    }

    public int GetX() {
        return this.x;
    }

    public int GetY() {
        return this.y;
    }

    public void SetName(final String name) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }

    public void PlantBomb(Sprite sprite, final String name, ENTITYID beid) {
        Gdx.app.log("Player, PlantBomb", "Bomb has been planted.");
        Bomb bomb = new Bomb(sprite,
            name,
            this.x,
            this.y,
            beid,
            this.bombPlacementListener);
        bomb.PlantBomb(this.bombDelayInS);
        this.bombPlacementListener.onBombPlaced(this.x, this.y, bomb);
        this.bombPlanted = true;
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

    public boolean GetIsAlive() {
        return this.isAlive;
    }

    public void Die() {
        this.isAlive = false;
        Gdx.app.log("Player", "Player " + this.name + " died!");
    }

    @Override
    public void render(SpriteBatch sbatch) {
//        Gdx.app.log("Player render", "Sprite X: " + this.sprite.getX() + ", Y: " + this.sprite.getY());
        this.sprite.draw(sbatch);
    }

    @Override
    public void dispose() {
    }
}
