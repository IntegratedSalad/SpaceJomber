package io.github.SpaceJomber.shared;

// Every message has a following structure:
// ID:PAYLOAD
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
                this.type = MessageType.MSG_SERVER_SENDS_SESSION_ID;
                break;
            }
            default: {
                throw new IllegalArgumentException();
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
