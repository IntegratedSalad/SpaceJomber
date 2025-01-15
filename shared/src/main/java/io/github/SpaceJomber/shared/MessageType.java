package io.github.SpaceJomber.shared;

public enum MessageType {
    MSG_USER_LOGGED_IN(1),
    MSG_USER_CREATED_LOBBY(2),
    MSG_USER_JOINED_LOBBY(3),
    MSG_USER_LEFT_LOBBY(4),

    MSG_SERVER_SENDS_SESSION_ID(5), // accept player
    MSG_SERVER_DENIES_ENTRY(6),
    MSG_SERVER_LOBBY_DOESNT_EXIST(7),
    MSG_SERVER_TERMINATE_CONNECTION(8),

    MSG_TWOWAY_SEND_PLAYER_NAME(20);

    private final int id;
    MessageType(int id) {
        this.id = id; // identyfikator
    }

    public int getTypeId() {
        return id;
    }
}
