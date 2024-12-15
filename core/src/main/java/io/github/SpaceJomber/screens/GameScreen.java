package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameScreen implements Screen {

    private Main game;
    private RenderingSystem renderingSystem;

    private Player instanceControlledPlayer = null;

    public GameScreen(RenderingSystem renderingSystem,
                      Main game) {
        this.renderingSystem = renderingSystem;
        this.game = game;

        // TODO: Pass information about type of game - multiplayer or local

        // Set up the Sprites
        renderingSystem.RegisterSprite("greenShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            11,
            16,
            16);

        // Set up players
        this.instanceControlledPlayer = new Player(renderingSystem.GetSprite("greenShip"),
            1,
            1,
            "Dodo");
        this.renderingSystem.AddRenderable(this.instanceControlledPlayer);

        // Setup input processor
        InputSystem ins = new InputSystem(this.instanceControlledPlayer);
        Gdx.input.setInputProcessor(ins);
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
