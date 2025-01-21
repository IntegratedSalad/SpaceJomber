package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.Bomb;
import io.github.SpaceJomber.entities.BombFire;
import io.github.SpaceJomber.entities.ENTITYID;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.listeners.SoundPlayerListener;
import io.github.SpaceJomber.systems.FireCollisionSystem;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

public class GameScreen implements Screen, SoundPlayerListener {

    protected Main game;
    protected RenderingSystem renderingSystem;
    protected FireCollisionSystem fireCollisionSystem;

    protected String shipColor;
    protected Player instanceControlledPlayer = null;

    protected final int startX;
    protected final int startY;

    protected String playerName;

//    protected Sound sound = Gdx.audio.newSound(Gdx.files.internal(".mp3"));

    public GameScreen(RenderingSystem renderingSystem,
                      Main game, final String shipColor,
                      final int startX,
                      final int startY,
                      final String playerName) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.shipColor = shipColor;
        this.startX = startX;
        this.startY = startY;
        this.playerName = playerName;

        // TODO: Pass information about type of game - multiplayer or local
        // After that, the players will be either controlled manually or they will be given
        // The MultiplayerController or something

        // Set up the Sprites
        renderingSystem.RegisterSprite("greenShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            11,
            16,
            16);

        renderingSystem.RegisterSprite("redShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            14,
            16,
            16);

        renderingSystem.RegisterSprite("blueShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            17,
            16,
            16);

        renderingSystem.RegisterSprite("blackShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            20,
            16,
            16);

        renderingSystem.RegisterSprite("greenBomb",
            "tiles/bomb_green.png",
            0,
            0,
            16,
            16);
        renderingSystem.RegisterSprite("blueBomb",
            "tiles/bomb_blue.png",
            0,
            0,
            16,
            16);
        renderingSystem.RegisterSprite("redBomb",
            "tiles/bomb_red.png",
            0,
            0,
            16,
            16);
        renderingSystem.RegisterSprite("blackBomb",
            "tiles/bomb_black.png",
            0,
            0,
            16,
            16);

        Bomb.intializeBombSprites(renderingSystem.GetSprite("redBomb"),
            renderingSystem.GetSprite("greenBomb"),
            renderingSystem.GetSprite("blueBomb"),
            renderingSystem.GetSprite("blackBomb"));

        // Bomb fire
        renderingSystem.RegisterSprite("leftFire",
            "tiles/Asset-Sheet-with-transparency.png",
            8,
            6,
            16,
            16);
        renderingSystem.RegisterSprite("rightFire",
            "tiles/Asset-Sheet-with-transparency.png",
            8,
            6,
            16,
            16);
        renderingSystem.RegisterSprite("upFire",
            "tiles/Asset-Sheet-with-transparency.png",
            8,
            6,
            16,
            16);
        renderingSystem.RegisterSprite("downFire",
            "tiles/Asset-Sheet-with-transparency.png",
            8,
            6,
            16,
            16);
        renderingSystem.RegisterSprite("centerFire",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            24,
            16,
            16);
        BombFire.initializeFireSprites(renderingSystem.GetSprite("centerFire"),
            renderingSystem.GetSprite("upFire"),
            renderingSystem.GetSprite("downFire"),
            renderingSystem.GetSprite("leftFire"),
            renderingSystem.GetSprite("rightFire"));

        this.SetupGame();
        Gdx.app.debug("GameScreen", "Initialized game screen...");
    }

    public void SetupGame() {
        // Set up players
        Gdx.app.debug("GameScreen, SetupGame", "SetupGame called...");

        ENTITYID idToSet = ENTITYID.UNKNOWN;
        switch (shipColor) {
            case "greenShip": {
                idToSet = ENTITYID.PLAYER_GREEN;
                break;
            }
            case "redShip": {
                idToSet = ENTITYID.PLAYER_RED;
                break;
            }
            case "blueShip": {
                idToSet = ENTITYID.PLAYER_BLUE;
                break;
            }
            case "blackShip": {
                idToSet = ENTITYID.PLAYER_BLACK;
                break;
            } default: {
                Gdx.app.debug("GameScreen", "Unknown ship color: " + shipColor);
            }
        }
        this.instanceControlledPlayer = new Player(renderingSystem.GetSprite(shipColor),
            this.startX,
            this.startY,
            this.playerName,
            idToSet, // ALWAYS THINK ABOUT DEFAULT VALUES!!!
            this.renderingSystem);
        this.renderingSystem.AddRenderable(this.instanceControlledPlayer);

        // Setup input processor
        InputSystem ins = new InputSystem(this.instanceControlledPlayer, this);
        Gdx.input.setInputProcessor(ins);

        // Setup Systems
        this.fireCollisionSystem = new FireCollisionSystem(renderingSystem.GetRenderableFlameQueue());
        this.fireCollisionSystem.addPlayer(this.instanceControlledPlayer);
    }

    public void OnPlayerDeath() {
        Gdx.app.debug("GameScreen, OnPlayerDeath", "Bye bye!");
        Gdx.app.exit();
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show() called");
        this.renderingSystem.LoadMap("gamemap.tmx");
        this.renderingSystem.SetTileSet();
        this.renderingSystem.SetTiledMapRenderer();
        this.renderingSystem.SetupCamera();

        this.instanceControlledPlayer.SetMapRf(this.renderingSystem.GetMap());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (this.instanceControlledPlayer != null && !this.instanceControlledPlayer.GetIsAlive()) {
            Gdx.app.log("render", "Player has died");
//            Gdx.app.exit();
//            this.instanceControlledPlayer = null;
//            Gdx.input.setInputProcessor(null); // await session termination by server, don't move and don't do anything
            this.OnPlayerDeath();
        }
        this.fireCollisionSystem.checkCollisions();
        this.renderingSystem.renderAll();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void PlayMoveSoundEffect() {
//        sound.play(1.0f);
    }
}
