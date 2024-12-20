package io.github.SpaceJomber.entities;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.SpaceJomber.systems.BombPlacementListener;
import io.github.SpaceJomber.systems.Renderable;

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

    public static Sprite centerFireSprite;
    public static Sprite upFireSprite;
    public static Sprite downFireSprite;
    public static Sprite leftFireSprite;
    public static Sprite rightFireSprite;

    public BombFire(int x, int y, ENTITYID eid, TiledMap tmRef) {
        this.x = x;
        this.y = y;
        this.eid = eid;
        this.tmRef = tmRef;
    }

    public static void initializeFireSprites(final Sprite c,
                                             final Sprite u,
                                             final Sprite d,
                                             final Sprite l,
                                             final Sprite r) {
        centerFireSprite = c;
        upFireSprite = u;
        upFireSprite.rotate(270);
        downFireSprite = d;
        downFireSprite.rotate(90);
        leftFireSprite = l;
        rightFireSprite = r;
        leftFireSprite.rotate(180);
    }

    public List<FireElement> SpreadFire() {
        List<FireElement> fireElements = new ArrayList<>();



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
