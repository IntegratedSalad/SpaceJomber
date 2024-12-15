package io.github.SpaceJomber.utils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapUtils {

    // TODO: add tile ids here
//    static int TILEID_EMPTY_SPACE =

    public static int GetCellIdAtXY(final TiledMapTileLayer layer, final int x, final int y) {
        return layer.getCell(x, y).getTile().getId();
    }

    public static TiledMapTileLayer GetLayer(TiledMap tm) {
        return (TiledMapTileLayer) tm.getLayers().get(0);
    }
}
