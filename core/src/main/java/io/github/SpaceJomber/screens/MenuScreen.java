package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.UIElements.ShapeTextButton;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

public class MenuScreen implements Screen {

    /*
        Screen has to receive a renderer and initialize needed assets
        TODO: Allow for the user name input below or in another Screen.
     */

    private RenderingSystem renderingSystem; // handles entities
//    private InputSystem inputSystem; // probably handled all by Stage
    private Stage stage; // handles UI elements

    private ShapeTextButton localGameButton;
    private ShapeTextButton multiplayerGameButton;
    private ShapeTextButton scoreButton;
    private ShapeTextButton exitButton;

    private final Texture backgroundTexture = new Texture("background.png");

    public MenuScreen(RenderingSystem renderingSystem) {
        this.renderingSystem = renderingSystem;
        this.renderingSystem.SetBackgroundImage(this.backgroundTexture);
//        this.renderingSystem.AddRenderable();
    }

    @Override
    public void show() {
//        Gdx.app.log("MenuScreen", "show() called");
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetFont();

        this.localGameButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "New Local Game",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
            );
        this.localGameButton.setSize(140, 40);
        this.localGameButton.setPosition((float) Gdx.graphics.getWidth() / 2,
            (float) Gdx.graphics.getHeight() / 2);
        this.stage.addActor(this.localGameButton);

        this.multiplayerGameButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "New Multiplayer Game",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.multiplayerGameButton.setSize(140, 40);

        this.scoreButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Score",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.scoreButton.setSize(140, 40);

        this.exitButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Exit",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.exitButton.setSize(140, 40);
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("MenuScreen", "render() called");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.renderingSystem.renderAll();
        this.stage.act(delta); // this updates actors
        this.stage.draw(); // this renders actors
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
        this.renderingSystem.dispose();
        this.backgroundTexture.dispose();
    }
}
