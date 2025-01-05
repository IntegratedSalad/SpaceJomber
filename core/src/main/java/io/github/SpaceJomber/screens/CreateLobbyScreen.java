package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Screen;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.systems.RenderingSystem;

public class CreateLobbyScreen implements Screen {

    private RenderingSystem renderingSystem;
    private Main game;

    public CreateLobbyScreen(RenderingSystem renderingSystem,
                             Main game) {
        this.renderingSystem = renderingSystem;
        this.game = game;

        // TODO: create multiplayer client and connect to the server
    }

    @Override
    public void show() {

        // TODO: Start game button

        // TODO: Cancel lobby button

        /* TODO: 4 frames with player's spaceships.
            Each player has a dedicated frame
            Every player's color is randomized.
            Players can change their nickname.
            Host is always upper-left and has a red border around spaceship image
         */

        // TODO: Connect


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
}
