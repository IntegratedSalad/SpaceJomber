package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Screen;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.systems.RenderingSystem;

public class MultiplayerGameScreen extends GameScreen {
    private MultiplayerClient multiplayerClient;
    private final String shipColor;

    public MultiplayerGameScreen(RenderingSystem renderingSystem, Main game,
                                 MultiplayerClient multiplayerClient, String shipColor) {
        super(renderingSystem, game);
        this.multiplayerClient = multiplayerClient;
        this.shipColor = shipColor;
    }

    @Override
    public void SetupGame() {
        super.SetupGame();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float v) {
        super.render(v);
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
