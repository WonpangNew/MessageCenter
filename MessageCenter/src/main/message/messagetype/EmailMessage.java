package main.message.messagetype;

import main.message.Message;

/**
 * Created by niuwanpeng on 2016/5/5.
 */
public class EmailMessage extends Message {

    public EmailMessage(String sendPerson, String receivePerson, String messageContent) {
        super(sendPerson, receivePerson, messageContent);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " ->" + super.toString();
    }
}
