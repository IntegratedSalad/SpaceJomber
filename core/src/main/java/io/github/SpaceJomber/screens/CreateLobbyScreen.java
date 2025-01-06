package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.UIElements.DynamicShapeTextButton;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateLobbyScreen implements Screen {

    private RenderingSystem renderingSystem;
    private Main game;
    private MultiplayerClient multiplayerClient;

    private ExecutorService multiplayerClientExecutor;

    private Stage stage;
    private SpriteBatch lobbySpriteBatch;

//    private ShapeTextButton startGameButton;
//    private ShapeTextButton cancelLobbyCreationButton;
//    private ShapeTextButton readyButton;

    DynamicShapeTextButton leftUpperPlayerButtonColor;
    DynamicShapeTextButton rightUpperPlayerButtonColor;
    DynamicShapeTextButton leftLowerPlayerButtonColor;
    DynamicShapeTextButton rightLowerPlayerButtonColor;

    public CreateLobbyScreen(RenderingSystem renderingSystem,
                             Main game, MultiplayerClient multiplayerClient) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.multiplayerClient = multiplayerClient;
        this.multiplayerClientExecutor = Executors.newFixedThreadPool(1);
        this.lobbySpriteBatch = new SpriteBatch();
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
        // TODO: create multiplayer client and connect to the server

        this.renderingSystem.RegisterSprite("greenShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            11,
            16,
            16);
        this.renderingSystem.RegisterSprite("redShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            14,
            16,
            16);
        this.renderingSystem.RegisterSprite("blueShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            17,
            16,
            16);
        this.renderingSystem.RegisterSprite("blackShip",
            "tiles/Asset-Sheet-with-transparency.png",
            1,
            20,
            16,
            16);

        SpriteDrawable spriteDrawableGreenShip =
            new SpriteDrawable(this.renderingSystem.GetSprite("greenShip"));
        SpriteDrawable spriteDrawableRedShip =
            new SpriteDrawable(this.renderingSystem.GetSprite("redShip"));
        SpriteDrawable spriteDrawableBlueShip =
            new SpriteDrawable(this.renderingSystem.GetSprite("blueShip"));
        SpriteDrawable spriteDrawableBlackShip =
            new SpriteDrawable(this.renderingSystem.GetSprite("blackShip"));

    }

    @Override
    public void show() {
        Gdx.app.debug("CreateLobbyScreen", "show called, connecting multiplayerClient..");
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);
        // TODO: Start game button

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetMainFont();
        float buttonWidth = 160;
        float buttonHeight = 35;
        float xBorderedImageButtonWidth = 70;
        float yBorderedImageButtonHeight = 70;

        float xTextButtonPosition = Gdx.graphics.getWidth() * 0.25f; // startingPosition
        float yTextButtonPosition = Gdx.graphics.getHeight() * 0.5f; // startingPosition
        float xBorderedImageButtonPosition = Gdx.graphics.getWidth() * 0.15f;
        float yBorderedImageButtonPosition = Gdx.graphics.getHeight() * 0.2f;
        int spacing = 100;

        final List<String> stringList = new ArrayList<>();
        stringList.add("Green");
        stringList.add("Red");
        stringList.add("Blue");
        stringList.add("Black");

//        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();

        this.leftUpperPlayerButtonColor = new DynamicShapeTextButton(this.renderingSystem.getShapeRenderer(),
            stringList, 0, textButtonStyle, Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);
        this.leftUpperPlayerButtonColor.setPosition(xTextButtonPosition, yTextButtonPosition);
        this.leftUpperPlayerButtonColor.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.leftUpperPlayerButtonColor);

        this.rightUpperPlayerButtonColor = new DynamicShapeTextButton(this.renderingSystem.getShapeRenderer(),
            stringList, 0, textButtonStyle, Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);
        this.rightUpperPlayerButtonColor.setPosition(xTextButtonPosition + buttonWidth + spacing, yTextButtonPosition);
        this.rightUpperPlayerButtonColor.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.rightUpperPlayerButtonColor);

        this.leftLowerPlayerButtonColor = new DynamicShapeTextButton(this.renderingSystem.getShapeRenderer(),
            stringList, 0, textButtonStyle, Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);
        this.leftLowerPlayerButtonColor.setPosition(xTextButtonPosition, yTextButtonPosition + buttonHeight + spacing);
        this.leftLowerPlayerButtonColor.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.leftLowerPlayerButtonColor);

        this.rightLowerPlayerButtonColor = new DynamicShapeTextButton(this.renderingSystem.getShapeRenderer(),
            stringList, 0, textButtonStyle, Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE);
        this.rightLowerPlayerButtonColor.setPosition(xTextButtonPosition + buttonWidth + spacing, yTextButtonPosition + buttonHeight + spacing);
        this.rightLowerPlayerButtonColor.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.rightLowerPlayerButtonColor);

        final Main mainGame = this.game;

        // TODO: Cancel lobby button

        /* TODO: 4 frames with player's spaceships.
            Each player has a dedicated frame
            Clicking changes color.
            Players can change their nickname.
            Host is always upper-left and has a red border around spaceship image
         */

        // TODO: Connect

        try {
            this.multiplayerClient.Connect();
            this.multiplayerClientExecutor.execute(this.multiplayerClient);
            final Message createdLobbyMessage = new Message(MessageType.MSG_USER_CREATED_LOBBY, ":NULL");
            this.multiplayerClient.SendMessage(createdLobbyMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
