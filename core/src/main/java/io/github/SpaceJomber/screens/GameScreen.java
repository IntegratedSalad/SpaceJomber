package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.systems.RenderingSystem;

public class GameScreen implements Screen {

    private Main game;
    private RenderingSystem renderingSystem;

    public GameScreen(RenderingSystem renderingSystem,
                      Main game) {
        this.renderingSystem = renderingSystem;
        this.game = game;

        // Set up the Sprites
        renderingSystem.RegisterSprite("greenShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            11,
            16,
            16);

        // Set up players
        Player p1 = new Player(renderingSystem.GetSprite("greenShip"), 1, 1);
        this.renderingSystem.AddRenderable(p1);
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show() called");
        this.renderingSystem.LoadMap("gamemap.tmx");
        this.renderingSystem.SetTileSet();
        this.renderingSystem.SetTiledMapRenderer();
        this.renderingSystem.SetupCamera();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
