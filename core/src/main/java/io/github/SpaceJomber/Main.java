/*
* Shared code.
*
* */

package io.github.SpaceJomber;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.screens.GameScreen;
import io.github.SpaceJomber.screens.MenuScreen;
import io.github.SpaceJomber.screens.ScoreScreen;
import io.github.SpaceJomber.systems.RenderingSystem;

/*
 * Main class should handle high-level game lifecycle
 * + Initialization of Systems
 * + ?
 *  */

    /*
        Server will send "update map" |
                         "initiate game" | other (...)
        messages to the client, and client will update them accordingly.

        Server can act as a broadcasting server.
        One client moves left. So, this client sends message:
        "I moved left, update it"

     */

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
//    private SpriteBatch batch;
//    private Texture image;
//
//    private TiledMap map;
//    private OrthogonalTiledMapRenderer renderer;
//    private OrthographicCamera camera;
//    private TiledMapTileLayer layer;

    private MultiplayerClient multiplayerClient;
    // TODO: Initialize networking module

    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private ScoreScreen scoreScreen;

    public Main(MultiplayerClient multiplayerClient) {
        this.multiplayerClient = multiplayerClient;
//        this.menuScreen = new MenuScreen(new RenderingSystem());
//        this.gameScreen = new GameScreen();
//        this.scoreScreen = new ScoreScreen();
    }

    @Override
    public void create() {
//        this.batch = new SpriteBatch();
//        this.image = new Texture("tiles/Moon Background/Surface_Layer4.png");
//        this.map = new TmxMapLoader().load("gamemap.tmx");
//        this.layer = (TiledMapTileLayer) this.map.getLayers().get(0);
//        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1/16f);
//        this.camera = new OrthographicCamera();
//        this.camera.setToOrtho(false, 15, 13);
//        this.camera.update();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem menuRenderingSystem = new RenderingSystem(camera);
        menuRenderingSystem.RegisterFont("roboticsfont.ttf");

        this.menuScreen = new MenuScreen(menuRenderingSystem);
        this.setScreen(this.menuScreen);
    }

    @Override
    public void render() {
        super.render();
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

//        this.camera.update();
//        renderer.setView(this.camera); // this is only a map renderer!!!
//        this.batch.begin();
//        this.batch.draw(image, 140, 210);
//        this.batch.end();

//        this.renderer.render();
    }

    @Override
    public void dispose() {
//        this.batch.dispose();
//        this.image.dispose();
    }
}
