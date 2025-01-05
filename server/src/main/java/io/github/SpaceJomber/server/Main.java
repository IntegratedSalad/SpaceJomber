package io.github.SpaceJomber.server;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(2137);
        server.Start();
    }
}
