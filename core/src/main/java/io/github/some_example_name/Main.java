package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer layer;

    /*
        Server will send "update map" |
                         "initiate game" | other (...)
        messages to the client, and client will update them accordingly.

        Server can act as a broadcasting server.
        One client moves left. So, this client sends message:
        "I moved left, update it"

     */

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.image = new Texture("tiles/Moon Background/Surface_Layer4.png");
        this.map = new TmxMapLoader().load("gamemap.tmx");
        this.layer = (TiledMapTileLayer) this.map.getLayers().get(0);
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/16f);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 15, 13);
        this.camera.update();
//        System.out.println(this.layer.getCell(0, 0).getTile().getId());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        this.camera.update();
        renderer.setView(this.camera);
//        this.batch.begin();
//        this.batch.draw(image, 140, 210);
//        this.batch.end();



        this.renderer.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.image.dispose();
    }
}
