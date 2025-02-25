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

import java.awt.*;

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
    private LobbyScreen lobbyScreen;
    private MultiplayerGameScreen multiplayerGameScreen;

    private EnterNameScreen enterNameScreen;
    private EnterLobbyUIDScreen enterLobbyUIDScreen;

    public Main(MultiplayerClient multiplayerClient) {
        this.multiplayerClient = multiplayerClient;
        System.out.println("This thread name: " + Thread.currentThread().getName());
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
        this.gameScreen = new GameScreen(gameRenderingSystem, this, "greenShip", 1, 1,
            "dodo");
        this.setScreen(this.gameScreen);
    }

    public void SetupMultiplayerGameScreen(MultiplayerClient multiplayerClient, final String shipColor,
                                           final int startX,
                                           final int startY,
                                           final String playerName) {

        System.out.println("SETUP MULTIPLAYER GAME SCREEN thread name: " + Thread.currentThread().getName());
        Gdx.app.debug("Main, SetupMultiplayerGameScreen", "Ship color: " + shipColor);

        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem gameRenderingSystem = new RenderingSystem(camera);
        gameRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.multiplayerGameScreen = new MultiplayerGameScreen(gameRenderingSystem,
            this, multiplayerClient, shipColor, startX, startY, playerName);
        this.setScreen(this.multiplayerGameScreen);
    }

    public void SetupMultiplayerDecisionScreen() {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem lobbyRenderingSystem = new RenderingSystem(camera);
        lobbyRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        this.multiplayerDecisionScreen = new MultiplayerDecisionScreen(lobbyRenderingSystem, this);
        this.setScreen(this.multiplayerDecisionScreen);
    }

    public void SetupLobbyScreen(final boolean isCreator, final String playerName, final String lobbyID) {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem lobbyRenderingSystem = new RenderingSystem(camera);
        lobbyRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        lobbyRenderingSystem.RegisterFontWithNumbers("fontwithnumbers.ttf");
        this.lobbyScreen = new LobbyScreen(lobbyRenderingSystem, this, this.multiplayerClient,
            isCreator, playerName, lobbyID);
        Gdx.app.debug("Main", "SetupCreateLobbyScreen called");
        this.setScreen(this.lobbyScreen);
    }

    public void SetupEnterNameScreen(final boolean isCreator, final String lobbyID) {
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem enterNameScreenRenderingSystem = new RenderingSystem(camera);
        enterNameScreenRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        enterNameScreenRenderingSystem.RegisterFontWithNumbers("fontwithnumbers.ttf");
        this.enterNameScreen = new EnterNameScreen(enterNameScreenRenderingSystem, this, isCreator, lobbyID);
        Gdx.app.debug("Main", "SetupCreateLobbyScreen called");
        this.setScreen(this.enterNameScreen);
    }

    public void SetupEnterLobbyUIDScreen(final boolean isCreator) {
        // no need to pass isCreator
        OrthographicCamera camera = new OrthographicCamera();
        RenderingSystem enterLobbyUIDRenderingSystem = new RenderingSystem(camera);
        enterLobbyUIDRenderingSystem.RegisterMainFont("roboticsfont.ttf");
        enterLobbyUIDRenderingSystem.RegisterFontWithNumbers("fontwithnumbers.ttf");
        this.enterLobbyUIDScreen = new EnterLobbyUIDScreen(this, enterLobbyUIDRenderingSystem, isCreator);
        this.setScreen(this.enterLobbyUIDScreen);
    }

    @Override
    public void render() {
        super.render();

        // TODO: ADJUST SCREEN INPUT CALCULATION WHEN RESIZING

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
