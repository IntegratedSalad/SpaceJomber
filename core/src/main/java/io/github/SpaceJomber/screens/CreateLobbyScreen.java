package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.io.IOException;
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

    public CreateLobbyScreen(RenderingSystem renderingSystem,
                             Main game, MultiplayerClient multiplayerClient) {
        this.renderingSystem = renderingSystem;
        this.game = game;
        this.multiplayerClient = multiplayerClient;
        this.multiplayerClientExecutor = Executors.newFixedThreadPool(1);
        this.lobbySpriteBatch = new SpriteBatch();
        this.renderingSystem.SetBackgroundImage(new Texture("background.png"));
        // TODO: create multiplayer client and connect to the server
    }

    @Override
    public void show() {
        Gdx.app.debug("CreateLobbyScreen", "show called, connecting multiplayerClient..");
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);
        // TODO: Start game button

        // TODO: Cancel lobby button

        /* TODO: 4 frames with player's spaceships.
            Each player has a dedicated frame
            Every player's color is randomized.
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
