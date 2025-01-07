package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.UIElements.ShapeTextButton;
import io.github.SpaceJomber.systems.RenderingSystem;

public class MultiplayerDecisionScreen implements Screen {

    private RenderingSystem renderingSystem;
    private Main game;
    private SpriteBatch lobbySpriteBatch;

    private Stage stage;

    private ShapeTextButton createLobbyButton;
    private ShapeTextButton joinLobbyButton;
    private ShapeTextButton backButton;

    public MultiplayerDecisionScreen(RenderingSystem renderingSystem,
                                     Main game) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.lobbySpriteBatch = new SpriteBatch();
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
    }

    @Override
    public void show() {
        Gdx.app.log("MultiplayerDecisionScreen", "show() called");
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetMainFont();
        float buttonWidth = 160;
        float buttonHeight = 35;

        // Centered at the middle of the screen.
        float xPosition = Gdx.graphics.getWidth() * 0.15f;
        float yPosition = Gdx.graphics.getHeight() / 2f - buttonHeight / 2;
        int spacing = 30;

        final Main mainGame = this.game;

        // TODO: Button: Create Lobby
        this.createLobbyButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Create Lobby",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.createLobbyButton.setSize(buttonWidth, buttonHeight);
        this.createLobbyButton.setPosition(xPosition, yPosition);
        this.stage.addActor(this.createLobbyButton);
        this.createLobbyButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                mainGame.SetupLobbyScreen(true);
                mainGame.SetupEnterNameScreen(true);
                Gdx.app.debug("MultiplayerDecisionScreen", "Enter Name Screen, transition to Create Lobby");
                return true;
            }
        });

        // TODO: Button: Join Lobby
        this.joinLobbyButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Join Lobby",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.joinLobbyButton.setSize(buttonWidth, buttonHeight);
        this.joinLobbyButton.setPosition(xPosition + (buttonWidth + spacing), yPosition);
        this.stage.addActor(this.joinLobbyButton);

        this.backButton = new ShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            "Back",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );
        this.backButton.setSize(buttonWidth, buttonHeight);
        this.backButton.setPosition(xPosition + (buttonWidth + spacing)*2, yPosition);
        this.stage.addActor(this.backButton);
        this.backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainGame.SetupMainMenu();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.lobbySpriteBatch.begin();
        this.lobbySpriteBatch.draw(this.renderingSystem.SetBackgroundImage(), 0, 0);
        this.lobbySpriteBatch.end();
        this.stage.act(delta); // this updates actors
        this.stage.draw(); // this renders actors
    }

    @Override
    public void resize(int i, int i1) {

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
