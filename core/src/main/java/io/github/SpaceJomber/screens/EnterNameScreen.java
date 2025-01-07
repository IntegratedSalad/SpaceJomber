package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.UIElements.ShapeTextButton;
import io.github.SpaceJomber.UIElements.ShapeTextField;
import io.github.SpaceJomber.systems.RenderingSystem;

public class EnterNameScreen implements Screen {

    private RenderingSystem renderingSystem;
    private Main game;

    private Stage stage;
    private SpriteBatch nameScreenSpriteBatch;

    private ShapeTextField textField;
    private ShapeTextButton enterButton;
    private ShapeTextButton cancelButton;

    private String playerName;

    private boolean isCreator;

    public EnterNameScreen(RenderingSystem renderingSystem,
                           Main game, final boolean isCreator) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.isCreator = isCreator;
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
        this.nameScreenSpriteBatch = new SpriteBatch();
    }

    public String GetPlayerName() {
        return this.playerName;
    }

    @Override
    public void show() {
        Gdx.app.debug("EnterNameScreen", "show called");
        this.stage = new Stage(new ScreenViewport());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetMainFont();

        float buttonWidth = 160;
        float buttonHeight = 35;

        float xTextField = Gdx.graphics.getWidth() / 2f - buttonWidth / 2; // startingPosition
        float yTextField = Gdx.graphics.getHeight() / 2f - buttonHeight / 2; // startingPosition

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = this.renderingSystem.GetFontWithNumbers();
        textFieldStyle.messageFont = this.renderingSystem.GetFontWithNumbers();
        textFieldStyle.fontColor = Color.WHITE;
        this.textField = new ShapeTextField("", textFieldStyle, this.renderingSystem.getShapeRenderer());
        this.textField.setMessageText("Enter your name");
        this.textField.setPosition(xTextField, yTextField);
        this.textField.setSize(168, textFieldStyle.font.getLineHeight());
        this.stage.addActor(this.textField);

        this.enterButton = new ShapeTextButton(this.renderingSystem.getShapeRenderer(),
            "Enter",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);

        this.enterButton.setPosition(xTextField, yTextField - this.textField.getHeight() - buttonHeight/2);
        this.enterButton.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.enterButton);

        this.cancelButton = new ShapeTextButton(this.renderingSystem.getShapeRenderer(),
            "Cancel",
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);

        this.cancelButton.setPosition(xTextField, this.enterButton.getY() - this.enterButton.getHeight());
        this.cancelButton.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.cancelButton);

        Gdx.input.setInputProcessor(this.stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.nameScreenSpriteBatch.begin();
        this.nameScreenSpriteBatch.draw(this.renderingSystem.SetBackgroundImage(), 0, 0);
        this.nameScreenSpriteBatch.end();
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

    }
}
