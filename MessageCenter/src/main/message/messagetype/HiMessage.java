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

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            logger.info("Hi发送成功");
        } catch (Exception e) {
            logger.info("Hi发送失败", e);
        }
    }
}
