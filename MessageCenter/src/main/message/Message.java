package main.message;

import org.apache.log4j.Logger;

/**
 * Created by niuwanpeng on 2016/5/5.
 */
public abstract class Message implements Runnable {
    private String sendPerson;
    private String receivePerson;
    private String messageContent;
    private String type;
    protected Logger logger = Logger.getLogger(Message.class);

    public Message(String sendPerson, String receivePerson, String messageContent) {
        this.sendPerson = sendPerson;
        this.receivePerson = receivePerson;
        this.messageContent = messageContent;
        this.type = this.getClass().getName();
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{messageContent='" + messageContent + "\', sendPerson='"
                + sendPerson + "\', receivePerson='" + receivePerson + "\'}";
    }
}
