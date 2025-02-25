package io.github.SpaceJomber.utils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapUtils {

    // TODO: add tile ids here
    public static final int TILEID_EMPTY_SPACE = 42;
    public static final int TILEID_DESTRUCTIBLE_TILE = 165;

    public static int GetCellIdAtXY(TiledMap tm, final int x, final int y) {
        TiledMapTileLayer tl = GetLayer(tm);
        return tl.getCell(x, y).getTile().getId();
    }

    public static void SetCellToNull(TiledMap tm, final int x, final int y) {
        TiledMapTileLayer tl = GetLayer(tm);
        tl.getCell(x, y).setTile(null);
    }

    public static void SetCell(TiledMap tm, final int x, final int y, TiledMapTileLayer.Cell cell) {
        TiledMapTileLayer tl = GetLayer(tm);
        tl.setCell(x, y, cell);
    }

    public static TiledMapTileLayer GetLayer(TiledMap tm) {
        return (TiledMapTileLayer) tm.getLayers().get(0);
    }
}
