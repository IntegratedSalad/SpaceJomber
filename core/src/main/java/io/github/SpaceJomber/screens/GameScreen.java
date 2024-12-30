package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.Bomb;
import io.github.SpaceJomber.entities.BombFire;
import io.github.SpaceJomber.entities.ENTITYID;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.systems.FireCollisionSystem;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

public class GameScreen implements Screen {

    private Main game;
    private RenderingSystem renderingSystem;
    private FireCollisionSystem fireCollisionSystem;

    private Player instanceControlledPlayer = null;

    public GameScreen(RenderingSystem renderingSystem,
                      Main game) {
        this.renderingSystem = renderingSystem;
        this.game = game;

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

        // Set up players
        this.instanceControlledPlayer = new Player(renderingSystem.GetSprite("greenShip"),
            1,
            1,
            "Dodo",
            ENTITYID.PLAYER_GREEN,
            this.renderingSystem);
        this.renderingSystem.AddRenderable(this.instanceControlledPlayer);

        // Setup input processor
        InputSystem ins = new InputSystem(this.instanceControlledPlayer);
        Gdx.input.setInputProcessor(ins);
        this.fireCollisionSystem = new FireCollisionSystem(renderingSystem.GetRenderableFlameQueue());
        this.fireCollisionSystem.addPlayer(this.instanceControlledPlayer);
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
//        this.renderingSystem
        // maybe clear rendering list
        // update
        if (!this.instanceControlledPlayer.GetIsAlive()) {
            Gdx.app.log("render", "Player has died");
            Gdx.app.exit();
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
}
