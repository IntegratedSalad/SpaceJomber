package io.github.SpaceJomber.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.UIElements.RenderableText;
import io.github.SpaceJomber.UIElements.ShapeTextButton;
import io.github.SpaceJomber.systems.RenderingSystem;

public class MenuScreen implements Screen {

    /*
        Screen has to receive a renderer and initialize needed assets
     */

    private RenderingSystem renderingSystem; // handles entities

    private Stage stage; // handles UI elements - an input processor

    private ShapeTextButton localGameButton;
    private ShapeTextButton lobbyButton;
    private ShapeTextButton scoreButton;
    private ShapeTextButton exitButton;
    private RenderableText titleText;

    private SpriteBatch menuSpriteBatch;
    private Main game;

    public MenuScreen(RenderingSystem renderingSystem,
                      Main game) {
        this.renderingSystem = renderingSystem;
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
        this.game = game;
        this.menuSpriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show() called");
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetMainFont();

        // Button dimensions
        float buttonWidth = 210;
        float buttonHeight = 40;

        // Starting position for the first button
        float xPosition = Gdx.graphics.getWidth() / 2f - buttonWidth / 2; // Centered horizontally
        float startY = Gdx.graphics.getHeight() * 0.3f; // Start at 30% of screen height
        float spacing = 15;

        final Main mainGame = this.game;

        // Create and position buttons
        this.localGameButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(), // TODO: Don't expose internals of the rendering system...
            "New Local Game",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.localGameButton.setSize(buttonWidth, buttonHeight);
        this.localGameButton.setPosition(xPosition, startY);
        this.stage.addActor(this.localGameButton);
        this.localGameButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainGame.SetupGameScreen();
                return true;
            }
        });

        this.lobbyButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Create or Join Lobby",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.lobbyButton.setSize(buttonWidth, buttonHeight);
        this.lobbyButton.setPosition(xPosition, startY - (buttonHeight + spacing) * 1);
        this.stage.addActor(this.lobbyButton);
        this.lobbyButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainGame.SetupMultiplayerDecisionScreen();
                return true;
            }
        });

        this.scoreButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Score",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.scoreButton.setSize(buttonWidth, buttonHeight);
        this.scoreButton.setPosition(xPosition, startY - (buttonHeight + spacing) * 2);
        this.stage.addActor(this.scoreButton);

        this.exitButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Exit",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.exitButton.setSize(buttonWidth, buttonHeight);
        this.exitButton.setPosition(xPosition, startY - (buttonHeight + spacing) * 3);
        this.stage.addActor(this.exitButton);
        this.exitButton.addListener(new InputListener() {
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               Gdx.app.exit();
               return true;
           }
        });

        GlyphLayout layout = new GlyphLayout();
        layout.setText(this.renderingSystem.GetMainTitleFont(),"SpaceJomber");
        final float textWidth = layout.width;
        final float titleXPosition = Gdx.graphics.getWidth() / 2f - textWidth / 2;
        final float titleYPosition = Gdx.graphics.getHeight() - 120;
        this.titleText = new RenderableText("SpaceJomber",
            titleXPosition,
            titleYPosition,
            this.renderingSystem.GetMainTitleFont());
//        this.renderingSystem.AddRenderable(this.titleText);
    }

    @Override
    public void render(float delta) {
//        Gdx.app.log("MenuScreen", "render() called");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.menuSpriteBatch.begin();
        this.menuSpriteBatch.draw(this.renderingSystem.SetBackgroundImage(), 0, 0);
        this.titleText.render(menuSpriteBatch);
        this.menuSpriteBatch.end();

//        this.renderingSystem.renderAll();
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
    }
}
