package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.ENTITYID;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.systems.FireCollisionSystem;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerGameScreen extends GameScreen {
    private MultiplayerClient multiplayerClient;

    private List<Player> players;

    public MultiplayerGameScreen(RenderingSystem renderingSystem, Main game,
                                 MultiplayerClient multiplayerClient, String shipColor,
                                 final int startX, final int startY,
                                 final String playerName) {
        super(renderingSystem, game, shipColor, startX, startY, playerName);
        this.multiplayerClient = multiplayerClient;
        this.players = new ArrayList<>();
        this.players.add(this.instanceControlledPlayer);
        Gdx.app.log("MultiplayerGameScreen", "X: " + startX + " Y: " + startY);
        Gdx.app.log("MultiplayerGameScreen", "Received player name: " + playerName);
    }

    @Override
    public void SetupGame() {
        this.shipColor += "Ship";
        Gdx.app.debug("MultiplayerGameScreen, SetupGame", "shipColor: " + this.shipColor);
        this.instanceControlledPlayer = new Player(renderingSystem.GetSprite(this.shipColor),
            this.startX,
            this.startY,
            this.shipColor.toUpperCase().split("SHIP")[0] + " Player", // TODO: acquire name entered
            ENTITYID.PLAYER_GREEN,
            this.renderingSystem);
        this.renderingSystem.AddRenderable(this.instanceControlledPlayer);

        // Setup input processor
        InputSystem ins = new InputSystem(this.instanceControlledPlayer);
        Gdx.input.setInputProcessor(ins);
        this.fireCollisionSystem = new FireCollisionSystem(renderingSystem.GetRenderableFlameQueue());
        this.fireCollisionSystem.addPlayer(this.instanceControlledPlayer);
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
