package io.github.SpaceJomber.networking;

public class MultiplayerClient {

    private int port;
    private String address;

    public MultiplayerClient() {
    }

    public MultiplayerClient(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
