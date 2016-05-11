package main.message.messagetype;

import main.message.Message;
import main.message.MessageType;

/**
 * Created by niuwanpeng on 2016/5/5.
 */
public class EmailMessage extends Message {

    public EmailMessage(String sendPerson, String receivePerson, String messageContent) {
        this.sendPerson = sendPerson;
        this.receivePerson = receivePerson;
        this.messageContent = messageContent;
        this.type = MessageType.EMAIL.toString();
    }
}
