package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.systems.RenderingSystem;

public class LocalGameSetupScreen implements Screen {

    private RenderingSystem renderingSystem;
    private Game game;

    public LocalGameSetupScreen(RenderingSystem renderingSystem,
                                Main game) {
        this.renderingSystem = renderingSystem;
        this.renderingSystem.SetBackgroundImage( new Texture("background.png"));
        this.game = game;

        // Selecting player 1 and player 2 sprite
        // Name of player 1 and player 2
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    // TODO:
    // Choose player A name and sprite
    // Choose player B name and sprite
}
