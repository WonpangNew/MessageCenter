package main.server;

import main.message.Message;
import org.apache.log4j.Logger;

/**
 * Created by niuwanpeng on 2016/5/11.
 *
 * 封装消息，用于处理发送消息的逻辑，使消息类中不再有业务逻辑
 */
public class SendMessage implements Runnable {
    private  Message message;
    private Logger logger = Logger.getLogger(SendMessage.class);

    public SendMessage(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            logger.info(this.message.getType()+"发送成功");
        } catch (InterruptedException e) {
            logger.error("发送失败", e);
        }
    }
}
