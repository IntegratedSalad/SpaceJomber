package io.github.SpaceJomber.shared;

// Every message has a following structure:
// ID:PAYLOAD|PAYLOAD2|PAYLOAD3
// TODO: Standardize that
public class Message {

    private MessageType type;
    private String payload;

    // Constructor for sending a message
    public Message(MessageType type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    // Constructor for receiving a message
    public Message(String message) {
        final String[] splitMessage = message.split(":");
        final String strID = splitMessage[0];
        final String strPayload = splitMessage[1];

        switch (strID) {
            case "1": {
                this.type = MessageType.MSG_USER_LOGGED_IN;
                break;
            }
            case "2": {
                this.type = MessageType.MSG_USER_CREATED_LOBBY;
                break;
            }
            case "3": {
                this.type = MessageType.MSG_USER_JOINED_LOBBY;
                break;
            }
            case "4": {
                this.type = MessageType.MSG_USER_LEFT_LOBBY;
                break;
            }
            case "5": {
                this.type = MessageType.MSG_USER_READY;
                break;
            }
            case "6": {
                this.type = MessageType.MSG_USER_GAMESCREEN_READY;
                break;
            }
            case "7": {
                this.type = MessageType.MSG_USER_MAKES_ACTION;
                break;
            }
            case "8": {
                this.type = MessageType.MSG_SERVER_SENDS_SESSION_ID;
                break;
            }
            case "9": {
                this.type = MessageType.MSG_SERVER_DENIES_ENTRY;
                break;
            }
            case "10": {
                this.type = MessageType.MSG_SERVER_LOBBY_DOESNT_EXIST;
                break;
            }
            case "11": {
                this.type = MessageType.MSG_SERVER_TERMINATE_CONNECTION;
                break;
            }
            case "12": {
                this.type = MessageType.MSG_SERVER_STARTS_SESSION;
                break;
            }
            case "13": {
                this.type = MessageType.MSG_SERVER_SENDS_PLAYER_NAMES;
                break;
            }
            case "14": {
                this.type = MessageType.MSG_SERVER_SENDS_PLAYER_MOVED;
                break;
            }
            case "15": {
                this.type = MessageType.MSG_SERVER_SENDS_PLAYER_PLANTS_BOMB;
                break;
            }
            case "16": {
                this.type = MessageType.MSG_SERVER_SESSION_END;
                break;
            }
            case "20": {
                this.type = MessageType.MSG_TWOWAY_SEND_PLAYER_NAME;
                break;
            }
            case "21": {
                this.type = MessageType.MSG_TWOWAY_SENDS_POSITION;
                break;
            }
            case "22": {
                this.type = MessageType.MSG_TWOWAY_PLAYER_DIES; // Server gets killerName but doesn't broadcast one
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown message type: " + strID);
            }
        }
        this.payload = strPayload;
    }

    public MessageType GetType() {
        return this.type;
    }

    public void SetPayload(String payload) {
        this.payload = payload;
    }

    public String GetPayload() {
        return this.payload;
    }

    // Return String from Message
    public String ConstructStringFromMessage() {
        String fullPayload = "";

        if (this.payload == null) {
            fullPayload += this.type.getTypeId();
        } else {
            fullPayload += this.type.getTypeId();
            fullPayload += ":";
            fullPayload += this.payload;
        }
        return fullPayload;
    }
}
