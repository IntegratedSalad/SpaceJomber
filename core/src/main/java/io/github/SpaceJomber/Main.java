/*
* Shared code.
*
* */

package io.github.SpaceJomber;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.screens.*;
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
    private MultiplayerDecisionScreen multiplayerDecisionScreen;
    private CreateLobbyScreen createLobbyScreen;
    private JoinLobbyScreen joinLobbyScreen;

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
        menuRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        menuRenderingSystem.RegisterMainTitleFont("roboticsfont.ttf"); // TODO: get rid of extra function

        this.menuScreen = new MenuScreen(menuRenderingSystem, this);
        this.setScreen(this.menuScreen);
    }

    public void SetupMainMenu() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem menuRenderingSystem = new RenderingSystem(camera);
        menuRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        menuRenderingSystem.RegisterMainTitleFont("roboticsfont.ttf"); // TODO: get rid of extra function

        this.menuScreen = new MenuScreen(menuRenderingSystem, this);
        this.setScreen(this.menuScreen);
    }

    public void SetupGameScreen() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem gameRenderingSystem = new RenderingSystem(camera);
        gameRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.gameScreen = new GameScreen(gameRenderingSystem, this);
        this.setScreen(this.gameScreen);
    }

    public void SetupMultiplayerDecisionScreen() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem lobbyRenderingSystem = new RenderingSystem(camera);
        lobbyRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.multiplayerDecisionScreen = new MultiplayerDecisionScreen(lobbyRenderingSystem, this);
        this.setScreen(this.multiplayerDecisionScreen);
    }

    public void SetupCreateLobbyScreen() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem lobbyRenderingSystem = new RenderingSystem(camera);
        lobbyRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.createLobbyScreen = new CreateLobbyScreen(lobbyRenderingSystem, this);
        this.setScreen(this.createLobbyScreen);
    }

    public void JoinLobbyScreen() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem lobbyRenderingSystem = new RenderingSystem(camera);
        lobbyRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.joinLobbyScreen = new JoinLobbyScreen(lobbyRenderingSystem, this);
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
