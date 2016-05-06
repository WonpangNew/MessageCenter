package main.message;

/**
 * Created by niuwanpeng on 2016/5/5.
 */
public abstract class Message {
    private String sendPerson;
    private String receivePerson;
    private String messageContent;

    public Message(String sendPerson, String receivePerson, String messageContent) {
        this.sendPerson = sendPerson;
        this.receivePerson = receivePerson;
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "Message{" + "messageContent='" + messageContent + '\'' + ", sendPerson='"
                + sendPerson + '\'' + ", receivePerson='" + receivePerson + '\'' + '}';
    }
}