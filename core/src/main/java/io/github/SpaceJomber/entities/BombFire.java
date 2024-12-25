package io.github.SpaceJomber.entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Timer;
import io.github.SpaceJomber.systems.BombPlacementListener;
import io.github.SpaceJomber.systems.FirePlacementListener;
import io.github.SpaceJomber.systems.Renderable;
import io.github.SpaceJomber.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;


/*
* Manager class for FireElement objects
* */
public class BombFire {

//    private Sprite sprite;
//    private String name;
    private int x;
    private int y;
    private ENTITYID eid;

    private TiledMap tmRef;
    private BombPlacementListener bombPlacementListener;
    private FirePlacementListener firePlacementListener;

    public static Sprite centerFireSprite;
    public static Sprite upFireSprite;
    public static Sprite downFireSprite;
    public static Sprite leftFireSprite;
    public static Sprite rightFireSprite;

    public BombFire(int x, int y, ENTITYID eid, TiledMap tmRef, FirePlacementListener firePlacementListener) {
        this.x = x;
        this.y = y;
        this.eid = eid;
        this.tmRef = tmRef;
        this.firePlacementListener = firePlacementListener;
    }

    public static void initializeFireSprites(final Sprite c,
                                             final Sprite u,
                                             final Sprite d,
                                             final Sprite l,
                                             final Sprite r) {
        centerFireSprite = c;
        upFireSprite = u;
        upFireSprite.setRotation(-90);
        upFireSprite.setOriginCenter();
        downFireSprite = d;
        downFireSprite.setRotation(90);
        downFireSprite.setOriginCenter();
        leftFireSprite = l;
        rightFireSprite = r;
        rightFireSprite.setRotation(180);
        rightFireSprite.setOriginCenter();
//        leftFireSprite.setRotation(180);
//        leftFireSprite.setOriginCenter();
    }

    public List<FireElement> SpreadFire() {
        List<FireElement> fireElements = new ArrayList<>();

        Gdx.app.log("SpreadFire", "Spreading fire at x: " + x + ", y: " + y);

        fireElements.add(new FireElement("fireElementCenter", centerFireSprite,
            this.x, this.y, ENTITYID.BOMB_FIRE_CENTER, this.tmRef, this.firePlacementListener));
        
        // Iterate leftside
        int mx = this.x - 1;
        int my = this.y;
        while (MapUtils.GetCellIdAtXY(this.tmRef, mx, this.y) == MapUtils.TILEID_EMPTY_SPACE) {
            FireElement fe = new FireElement("fireElementLeft", leftFireSprite, mx,
                this.y, ENTITYID.BOMB_FIRE_LEFT, this.tmRef, this.firePlacementListener);
            fireElements.add(fe);
            Gdx.app.log("SpreadFire", "Left fire at x: " + mx + ", y: " + y);
            mx--;
        }
        if (MapUtils.GetCellIdAtXY(this.tmRef, mx, this.y) == MapUtils.TILEID_DESTRUCTIBLE_TILE) {
            FireElement nfe = new FireElement("null", leftFireSprite, mx, this.y, ENTITYID.BOMB_FIRE_UP, this.tmRef, this.firePlacementListener);
            fireElements.add(nfe);
            fireElements.get(fireElements.size() - 1).SetDestroysTile(true);
        }
        // Iterate rightside
        mx = this.x + 1;
        while (MapUtils.GetCellIdAtXY(this.tmRef, mx, this.y) == MapUtils.TILEID_EMPTY_SPACE) {
            FireElement fe = new FireElement("fireElementRight", rightFireSprite, mx,
                this.y, ENTITYID.BOMB_FIRE_RIGHT, this.tmRef, this.firePlacementListener);
            fireElements.add(fe);
            Gdx.app.log("SpreadFire", "Right fire at x: " + mx + ", y: " + y);
            mx++;
        }
        if (MapUtils.GetCellIdAtXY(this.tmRef, mx, this.y) == MapUtils.TILEID_DESTRUCTIBLE_TILE) {
            FireElement nfe = new FireElement("null", rightFireSprite, mx, this.y, ENTITYID.BOMB_FIRE_UP, this.tmRef, this.firePlacementListener);
            fireElements.add(nfe);
            fireElements.get(fireElements.size() - 1).SetDestroysTile(true);
            // Or maybe just delete it?
        }
        // Iterate upside
        mx = this.x;
        my = this.y + 1;
        while (MapUtils.GetCellIdAtXY(this.tmRef, this.x, my) == MapUtils.TILEID_EMPTY_SPACE) {
            FireElement fe = new FireElement("fireElementUp", upFireSprite, this.x,
                my, ENTITYID.BOMB_FIRE_UP, this.tmRef, this.firePlacementListener);
            fireElements.add(fe);
            Gdx.app.log("SpreadFire", "Up fire at x: " + x + ", y: " + my);
            my++;
        }
        if (MapUtils.GetCellIdAtXY(this.tmRef, this.x, my) == MapUtils.TILEID_DESTRUCTIBLE_TILE) {
            FireElement nfe = new FireElement("null", upFireSprite, this.x, my, ENTITYID.BOMB_FIRE_UP, this.tmRef, this.firePlacementListener);
            fireElements.add(nfe);
            fireElements.get(fireElements.size() - 1).SetDestroysTile(true);
        }
        // Iterate downside
        mx = this.x;
        my = this.y - 1;
        while (MapUtils.GetCellIdAtXY(this.tmRef, this.x, my) == MapUtils.TILEID_EMPTY_SPACE) {
            FireElement fe = new FireElement("fireElementDown", downFireSprite, this.x,
                my, ENTITYID.BOMB_FIRE_DOWN, this.tmRef, this.firePlacementListener);
            fireElements.add(fe);
            Gdx.app.log("SpreadFire", "Down fire at x: " + x + ", y: " + my);
            my--;
        }
        if (MapUtils.GetCellIdAtXY(this.tmRef, this.x, my) == MapUtils.TILEID_DESTRUCTIBLE_TILE) {
            FireElement nfe = new FireElement("null", downFireSprite, this.x, my, ENTITYID.BOMB_FIRE_UP, this.tmRef, this.firePlacementListener);
            fireElements.add(nfe);
            fireElements.get(fireElements.size() - 1).SetDestroysTile(true);
        }

        Gdx.app.log("SpreadFire", "fireElements length = " + fireElements.size());
        return fireElements;
    }

//    @Override
//    public void render(SpriteBatch sbatch) {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//    @Override
//    public ENTITYID GetEntityID() {
//        return null;
//    }
}
