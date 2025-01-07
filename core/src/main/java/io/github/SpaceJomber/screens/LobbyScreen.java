package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.UIElements.DynamicShapeTextButton;
import io.github.SpaceJomber.UIElements.RenderableText;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;
import io.github.SpaceJomber.systems.LobbyUIUpdateListener;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LobbyScreen implements Screen, LobbyUIUpdateListener {

    private RenderingSystem renderingSystem;
    private Main game;
    private MultiplayerClient multiplayerClient;

    private ExecutorService multiplayerClientExecutor;

    private Stage stage;
    private SpriteBatch lobbySpriteBatch;

    private final boolean isCreator;

    private DynamicShapeTextButton colorChangeTextButton;

    private String lobbyID;
    private RenderableText lobbyIDText;

    private RenderableText changeColorText;

    public LobbyScreen(RenderingSystem renderingSystem,
                             Main game, MultiplayerClient multiplayerClient, final boolean isCreator) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.multiplayerClient = multiplayerClient;
        this.multiplayerClientExecutor = Executors.newFixedThreadPool(1);
        this.lobbySpriteBatch = new SpriteBatch();
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
        this.isCreator = isCreator;
        this.multiplayerClient.SetLobbyUIUpdateListener(this);

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

        // Connect
        try {
            this.multiplayerClient.SetIsLobbyHost(this.isCreator);
            this.multiplayerClient.Connect();
            this.multiplayerClientExecutor.execute(this.multiplayerClient);
            final Message createdLobbyMessage = new Message(MessageType.MSG_USER_CREATED_LOBBY, ":NULL");
            this.multiplayerClient.SendMessage(createdLobbyMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void show() {

        Gdx.app.debug("LobbyScreen", "show called");
        this.stage = new Stage(new ScreenViewport());
        // TODO: Start game button

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.renderingSystem.GetMainFont();
        float buttonWidth = 160;
        float buttonHeight = 35;
        float xBorderedImageButtonWidth = 70;
        float yBorderedImageButtonHeight = 70;

        float xTextButtonPosition = Gdx.graphics.getWidth() / 2f - buttonWidth / 2; // startingPosition
        float yTextButtonPosition = Gdx.graphics.getHeight() * 0.3f; // startingPosition
        float xBorderedImageButtonPosition = Gdx.graphics.getWidth() * 0.15f;
        float yBorderedImageButtonPosition = Gdx.graphics.getHeight() * 0.2f;
        int spacing = 100;

        final float sessionIDTextXPosition =  Gdx.graphics.getWidth() / 2f - buttonWidth / 2;
        final float sessionIDTextYPosition = Gdx.graphics.getHeight() * 0.1f;
        this.lobbyIDText = new RenderableText("XXXXXXXX", sessionIDTextXPosition + 40,
            sessionIDTextYPosition, renderingSystem.GetFontWithNumbers());

        List<String> colorList = new ArrayList<>();
        colorList.add("green");
        colorList.add("red");
        colorList.add("blue");
        colorList.add("black");

        this.colorChangeTextButton = new DynamicShapeTextButton(
            this.renderingSystem.getShapeRenderer(),
            colorList,
            0,
            textButtonStyle,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.WHITE
        );

        this.colorChangeTextButton.setPosition(xTextButtonPosition, yTextButtonPosition);
        this.colorChangeTextButton.setSize(buttonWidth, buttonHeight);
        this.stage.addActor(this.colorChangeTextButton);

        this.changeColorText = new RenderableText("Change color", this.colorChangeTextButton.getX() + 20,
            this.colorChangeTextButton.getY() + this.colorChangeTextButton.getWidth()/2 - 21, this.renderingSystem.GetFontWithNumbers());

        if (this.isCreator) {
            // TODO: Show button for Cancelling the lobby/starting
            Gdx.app.debug("LobbyScreen", "This client is the host.");
        }

        /* TODO: 4 frames with player's spaceships.
            Each player has a dedicated frame
            Clicking changes color.
            Players can change their nickname.
            Host is always upper-left and has a red border around spaceship image

            OR:
            Only one image with randomized space ship color.
            Each client at connection is assigned a random spaceship color.

            At bottom: names of other players.
            Upon joining and creation user sets their name which cannot be changed.

            Maybe other way:

            Player creates a lobby and he gets the lobby UID.
            This UID is shown in the upper side of the screen.
            There is no separate Join/Create lobby screen.
            Clicking "Create Lobby" sends message to the server with message,
            that this user is the creator. Also, in MultiplayerClient boolean
            "lobbyHost" is set to True.
            Server responds with Lobby UUID.
            Players can still change colors.

         */

        Gdx.input.setInputProcessor(this.stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.lobbySpriteBatch.begin();
        this.lobbySpriteBatch.draw(this.renderingSystem.SetBackgroundImage(), 0, 0);
        this.lobbyIDText.render(this.lobbySpriteBatch);
        this.changeColorText.render(this.lobbySpriteBatch);
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
        this.renderingSystem.dispose();
    }

    @Override
    public void onLobbyIDReceived(String lobbyID) {
        Gdx.app.postRunnable(() -> this.lobbyIDText.SetText(lobbyID));
        Gdx.app.debug("LobbyScreen", "onLobbyIDReceived: " + lobbyID);
    }
}
