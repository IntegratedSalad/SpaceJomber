package io.github.SpaceJomber.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.SpaceJomber.Main;
import io.github.SpaceJomber.entities.Bomb;
import io.github.SpaceJomber.entities.ENTITYID;
import io.github.SpaceJomber.entities.Player;
import io.github.SpaceJomber.listeners.BombPlacementListener;
import io.github.SpaceJomber.listeners.MultiplayerGameListener;
import io.github.SpaceJomber.networking.MultiplayerClient;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.shared.MessageType;
import io.github.SpaceJomber.systems.FireCollisionSystem;
import io.github.SpaceJomber.systems.InputSystem;
import io.github.SpaceJomber.systems.MessageInputSystem;
import io.github.SpaceJomber.systems.RenderingSystem;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerGameScreen extends GameScreen implements MultiplayerGameListener {
    private MultiplayerClient multiplayerClient;

    private List<Player> players;

    public MultiplayerGameScreen(RenderingSystem renderingSystem, Main game,
                                 MultiplayerClient multiplayerClient, String shipColor,
                                 final int startX, final int startY,
                                 final String playerName) {
        super(renderingSystem, game, shipColor, startX, startY, playerName);
        this.multiplayerClient = multiplayerClient;
        this.multiplayerClient.SetMultiplayerGameListener(this);
        // Setup input processor
        MessageInputSystem ins = new MessageInputSystem(this.instanceControlledPlayer,
            this.multiplayerClient, this);
        Gdx.input.setInputProcessor(ins);
        this.players = new ArrayList<>();
        this.players.add(this.instanceControlledPlayer);
        Gdx.app.log("MultiplayerGameScreen", "X: " + startX + " Y: " + startY);
        Gdx.app.log("MultiplayerGameScreen", "Received player name: " + playerName);
        this.InitializeMultiplayerPlayers();
        Message msgGameScreenReady = new Message(MessageType.MSG_USER_GAMESCREEN_READY, "NULL");
        this.multiplayerClient.SendMessage(msgGameScreenReady); // send AFTER InitializeMultiplayerPlayers
    }

    private void InitializeMultiplayerPlayers() {
        // Setup Players
        final List<String> spriteStrings = new ArrayList<>();
        spriteStrings.add("greenShip");
        spriteStrings.add("redShip");
        spriteStrings.add("blueShip");
        spriteStrings.add("blackShip");
        for (int i = 0; i < 4; i ++) {
            final String spriteColor = spriteStrings.get(i);
            ENTITYID eid = ENTITYID.UNKNOWN;
            switch (spriteColor) {
                case "greenShip": {
                    eid = ENTITYID.PLAYER_GREEN;
                    break;
                }
                case "redShip": {
                    eid = ENTITYID.PLAYER_RED;
                    break;
                }
                case "blueShip": {
                    eid = ENTITYID.PLAYER_BLUE;
                    break;
                }
                case "blackShip": {
                    eid = ENTITYID.PLAYER_BLACK;
                    break;
                } default: {
                    Gdx.app.debug("MultiplayerGameScreen", "Unknown sprite color: " + spriteColor);
                }
            }
            if (spriteColor.equals(this.shipColor)) {
                continue;
            }
            Player playerToAdd = new Player(this.renderingSystem.GetSprite(spriteColor),
                -1, -1, "BLANK", eid, this.renderingSystem);
            this.players.add(playerToAdd);
            this.renderingSystem.AddRenderable(playerToAdd);
            this.fireCollisionSystem.addPlayer(playerToAdd);
        }
    }

    private Player GetPlayerByName(String name) {
        for (Player player : this.players) {
            if (player.GetName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public void SetupGame() {
        this.shipColor += "Ship";
        Gdx.app.debug("MultiplayerGameScreen, SetupGame", "shipColor: " + this.shipColor);

        ENTITYID idToSet = ENTITYID.UNKNOWN;
        switch (shipColor) {
            case "greenShip": {
                idToSet = ENTITYID.PLAYER_GREEN;
                break;
            }
            case "redShip": {
                idToSet = ENTITYID.PLAYER_RED;
                break;
            }
            case "blueShip": {
                idToSet = ENTITYID.PLAYER_BLUE;
                break;
            }
            case "blackShip": {
                idToSet = ENTITYID.PLAYER_BLACK;
                break;
            } default: {
                Gdx.app.debug("GameScreen", "Unknown ship color: " + shipColor);
            }
        }

        this.instanceControlledPlayer = new Player(renderingSystem.GetSprite(this.shipColor),
            this.startX,
            this.startY,
            this.playerName,
            idToSet, // ALWAYS THINK ABOUT DEFAULT VALUES!!!
            this.renderingSystem);
        this.renderingSystem.AddRenderable(this.instanceControlledPlayer);

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

    @Override
    public synchronized void onPlayerMove(String name, int newPositionX, int newPositionY) {
        Gdx.app.postRunnable(() -> {
            if (name.equals(this.playerName)) return;
            Player playerToMove = GetPlayerByName(name);
            if (playerToMove != null && playerToMove != this.instanceControlledPlayer) {
                if (playerToMove.GetX() == playerToMove.GetY() && playerToMove.GetX() == -1) {
                    playerToMove.Teleport(newPositionX, newPositionY);
                    Gdx.app.debug("MultiplayerGameScreen", "Player " + playerToMove.GetName() +
                        " teleported to position " + newPositionX + ", " + newPositionY);
                } else { // move normally
                    playerToMove.Teleport(newPositionX, newPositionY);
                    Gdx.app.debug("MultiplayerGameScreen", "Player moved" + playerToMove.GetName() +
                        " to position " + newPositionX + ", " + newPositionY);
                }
            } else {
                Gdx.app.debug("MultiplayerGameScreen", "Attempt to move player: " + name +
                    " to position:" + newPositionX + " " + newPositionY + " which doesn't exist");
            }
        });
    }

    @Override
    public void onPlayerReceiveNames(final String[] payload) {
        // TODO: Sync not on colors but ids
        Gdx.app.postRunnable(() -> {
            for (int i = 0; i < payload.length; i+=2) {
                final String name = payload[i];
                final String color = payload[i+1];
                for (Player player : this.players) {
                    switch (color) {
                        case "green": {
                            if (player.GetEntityID() == ENTITYID.PLAYER_GREEN) {
                                player.SetName(name);
                                Gdx.app.debug("MultiplayerGameScreen, onPlayerReceiveNames",
                                    "matched " + playerName + " with green color!");
                            }
                            break;
                        }
                        case "red": {
                            if (player.GetEntityID() == ENTITYID.PLAYER_RED) {
                                player.SetName(name);
                                Gdx.app.debug("MultiplayerGameScreen, onPlayerReceiveNames",
                                    "matched " + playerName + " with red color!");
                            }
                            break;
                        }
                        case "blue": {
                            if (player.GetEntityID() == ENTITYID.PLAYER_BLUE) {
                                player.SetName(name);
                                Gdx.app.debug("MultiplayerGameScreen, onPlayerReceiveNames",
                                    "matched " + playerName + " with blue color!");
                            }
                            break;
                        }
                        case "black": {
                            if (player.GetEntityID() == ENTITYID.PLAYER_BLACK) {
                                player.SetName(name);
                                Gdx.app.debug("MultiplayerGameScreen, onPlayerReceiveNames",
                                    "matched " + playerName + " with black color!");
                            }
                            break;
                        } default: {
                            Gdx.app.debug("MultiplayerGameScreen, onPlayerReceiveNames",
                                "Unknown color: " + color);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onPlayerPlantsBomb(String playerName, int posX, int posY) {
        Gdx.app.postRunnable(() -> {
            Player p = this.GetPlayerByName(playerName);

            if (p.GetName().equals(this.playerName)) {return;}

            Sprite bombSprite = null;
            final ENTITYID beid = p.GetBombID();
            Gdx.app.debug("onPlayerPlantsbomb", "Beid: " + beid);
            if (beid != null) {
                switch (beid) {
                    case BOMB_RED: {
                        bombSprite = Bomb.redBombSprite;
                        break;
                    }
                    case BOMB_BLACK: {
                        bombSprite = Bomb.blackBombSprite;
                        break;
                    }
                    case BOMB_BLUE: {
                        bombSprite = Bomb.blueBombSprite;
                        break;
                    }
                    case BOMB_GREEN: {
                        bombSprite = Bomb.greenBombSprite;
                        break;
                    } default: {
                        Gdx.app.debug("InputSystem, OnActionKey", "Bomb id null!");
                        break;
                    }
                }
            }
            bombSprite = new Sprite(bombSprite);
            p.PlantBomb(bombSprite, Bomb.GetBombNameFromID(beid), beid);
        });
    }
}
