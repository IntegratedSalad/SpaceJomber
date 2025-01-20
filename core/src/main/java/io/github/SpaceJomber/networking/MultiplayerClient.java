package io.github.SpaceJomber.networking;

import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.listeners.MultiplayerGameListener;
import io.github.SpaceJomber.screens.MultiplayerGameScreen;
import io.github.SpaceJomber.shared.Message;
import io.github.SpaceJomber.listeners.LobbyUIUpdateListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.*;

/*
* This entity communicates with server -> sends messages to the socket.
* Server handles these via ClientHandler
* */
public class MultiplayerClient implements Runnable {

    private int port;
    private String address;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BlockingQueue<Message> messageQueue; // messageQueueOut

    private ExecutorService messageHandlerExecutor;

    private boolean isLobbyHost = false;

    private LobbyUIUpdateListener lobbyListener;
    private MultiplayerGameListener multiplayerGameListener;

    private boolean isRunning = true;

    public MultiplayerClient(String address, int port) {
        this.address = address;
        this.port = port;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.messageHandlerExecutor = Executors.newFixedThreadPool(2);
    }

    public void SetLobbyUIUpdateListener(LobbyUIUpdateListener lobbyListener) {
        this.lobbyListener = lobbyListener;
    }

    public void SetMultiplayerGameListener(MultiplayerGameListener multiplayerGameListener) {
        this.multiplayerGameListener = multiplayerGameListener;
    }

    public int GetPort() {
        return port;
    }

    public void SetPort(int port) {
        this.port = port;
    }

    public String GetAddress() {
        return address;
    }

    public void SetAddress(String address) {
        this.address = address;
    }

    public boolean GetIsLobbyHost() {
        return isLobbyHost;
    }

    public void SetIsLobbyHost(final boolean isLobbyHost) {
        this.isLobbyHost = isLobbyHost;
    }

    public void SendMessage(Message message) {
        Gdx.app.debug("MultiplayerClient", "Sending message: " + message.GetType().toString());
        try {
            this.messageQueue.put(message);
            Gdx.app.debug("MultiplayerClient, SendMessage", "Message queue size: " + this.messageQueue.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to put message in queue!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void Connect() throws IOException {
        Gdx.app.debug("MultiplayerClient", "Connecting to " + this.address + ":" + this.port);
        this.socket = new Socket(this.address, this.port);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public void Disconnect() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
        this.messageHandlerExecutor.shutdownNow();
        this.isRunning = false;
    }

    private void HandleOutgoingMessages() throws InterruptedException {
        while (true) {
            try {
                Message messageOut;
                Gdx.app.debug("MultiplayerClient, HandleOutgoingMessages", "Message queue size: " + this.messageQueue.size());
                messageOut = this.messageQueue.take();
                final String payload = messageOut.ConstructStringFromMessage();
                this.out.println(payload); // Send message to server}
            } catch (InterruptedException e) {
                throw new InterruptedException();
            }
        }
    }

    private void HandleIncomingMessages() throws RuntimeException {
        Message messageIn;
        while (true) {
            try {
                String rawServerResponse = in.readLine();
                if (rawServerResponse != null) {
                    messageIn = new Message(rawServerResponse);
                    System.out.println("Raw server response received: " + rawServerResponse);

                    switch (messageIn.GetType()) {
                        case MSG_SERVER_SENDS_SESSION_ID: {
                            Gdx.app.debug("MultiplayerClient",
                                "received MSG_SERVER_SENDS_SESSION_ID with payload: " + messageIn.GetPayload());
                            this.lobbyListener.onLobbyIDReceived(messageIn.GetPayload());
                            break;
                        }
                        case MSG_SERVER_TERMINATE_CONNECTION: {
                            Gdx.app.debug("MultiplayerClient",
                                "Received connection termination from the server");
                            Disconnect();
                            return;
                        }
                        case MSG_SERVER_LOBBY_DOESNT_EXIST: {
                            Disconnect();
                            Gdx.app.debug("MultiplayerClient", "Received lobby does not exist");
                            return;
                        }
                        case MSG_TWOWAY_SEND_PLAYER_NAME: {
                            Gdx.app.debug("MultiplayerClient",
                                "Received player name " + messageIn.GetPayload());
                            this.lobbyListener.onLobbyPlayerJoined(messageIn.GetPayload());
                            break;
                        }
                        case MSG_SERVER_STARTS_SESSION: {
                            Gdx.app.debug("MultiplayerClient", "Received server starts session");

                            Gdx.app.debug("MultiplayerClient", "Positions: " + messageIn.GetPayload());

                            final String[] positions = messageIn.GetPayload().split(" ");
                            final int positionX = Integer.parseInt(positions[0]);
                            final int positionY = Integer.parseInt(positions[1]);

                            this.lobbyListener.onSessionStarted(positionX, positionY);
                            break;
                        }
                        case MSG_TWOWAY_SENDS_POSITION: {
                            final String[] payload = messageIn.GetPayload().split(" ");
                            System.out.println(Arrays.toString(payload));
                            final int positionX = Integer.parseInt(payload[0]);
                            final int positionY = Integer.parseInt(payload[1]);
                            final String name = payload[2];

                            Gdx.app.debug("MultiplayerClient", "Received MSG_TWOWAY_SENDS_POSITION. " +
                                "Payload:");
                            Gdx.app.debug("MultiplayerClient", "Position: " + positionX + ", " +
                                positionY + ": " + name);
                            this.multiplayerGameListener.onPlayerMove(name, positionX, positionY);
                            break;
                        }
                        case MSG_SERVER_SENDS_PLAYER_NAMES: {
                            Gdx.app.debug("MultiplayerClient", "Received MSG_SERVER_SENDS_PLAYER_NAMES" +
                                " " + messageIn.GetPayload());

                            final String[] payload = messageIn.GetPayload().split(" ");
                            for (int i = 0; i < payload.length; i+=2) {
                                final String name = payload[i];
                                final String color = payload[i+1];

                                Gdx.app.debug("MultiplayerClient", "MSG_SERVER_SENDS_PLAYER_NAMES" +
                                    "name: " + name + ", color: " + color);
                            }
                            this.multiplayerGameListener.onPlayerReceiveNames(payload);
                            break;
                        }
                        case MSG_SERVER_SENDS_PLAYER_MOVED: {
                            Gdx.app.debug("MultiplayerClient", "Received MSG_SERVER_SENDS_PLAYER_MOVED");
                            final String[] payload = messageIn.GetPayload().split(" ");
                            final int x = Integer.parseInt(payload[0]);
                            final int y = Integer.parseInt(payload[1]);
                            final String name = payload[2];

                            Gdx.app.debug("MultiplayerClient", name + " moves to X: " + x + " Y: " + y);
                            this.multiplayerGameListener.onPlayerMove(name, x, y);
                            break;
                        }
                        default: {
                            Gdx.app.debug("MultiplayerClient", "Received unknown server response: " +
                                rawServerResponse);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {

        System.out.println("MultiplayerClient run() new thread called from " + Thread.currentThread().getName());
        this.messageHandlerExecutor.execute(this::HandleIncomingMessages);
        this.messageHandlerExecutor.execute(() -> {
            try {
                HandleOutgoingMessages();
            } catch (InterruptedException e) {
                Gdx.app.debug("MultiplayerClient", e.getMessage());
                isRunning = false;
            }
        });

        try {
            this.messageHandlerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

//        while (this.isRunning) {
//            // Thread idle
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }


}
