package main.message.messagetype;

import main.message.Message;

/**
 * Created by niuwanpeng on 2016/5/5.
 */
public class HiMessage extends Message {

    public HiMessage(String sendPerson, String receivePerson, String messageContent) {
        super(sendPerson, receivePerson, messageContent);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " ->" + super.toString();
    }
}
