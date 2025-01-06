package io.github.SpaceJomber.networking;

import com.badlogic.gdx.Gdx;
import io.github.SpaceJomber.shared.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

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

    public MultiplayerClient(String address, int port) {
        this.address = address;
        this.port = port;
        this.messageQueue = new LinkedBlockingQueue<>();
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

    public void SendMessage(Message message) {
        Gdx.app.debug("MultiplayerClient", "Sending message: " + message.GetType().toString());
        try {
            this.messageQueue.put(message);
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

    @Override
    public void run() {
        while (true) {
            Message message;
            try {
                message = this.messageQueue.take();
                final String payload = message.ConstructFullPayload();
                this.out.println(payload);



                if (in.ready()) { // listen for server response
                    String rawServerResponse = in.readLine();
                    System.out.println("Raw server response received: " + rawServerResponse);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
