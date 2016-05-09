package main.demo;

import main.message.messagetype.EmailMessage;
import main.message.messagetype.HiMessage;
import main.message.messagetype.ShortMessage;
import main.messagecenter.MessageCenter;

/**
 * Created by niuwanpeng on 2016/5/9.
 */
public class MessageDemo extends Thread {

    /**
     * 模拟用户随机发送消息
     */
    @Override
    public void run() {
        while (true) {
            double random = Math.random();
            if (random < 0.3) {
                MessageCenter.push(new HiMessage("tomHi", "LucyHi", "Hi"));
            }
            else if (random < 0.7 && random > 0.3) {
                MessageCenter.push(new EmailMessage("tomEmail", "LucyEmail", "Email"));
            }
            else {
                MessageCenter.push(new ShortMessage("tomShort", "LucyShort", "Short"));
            }
        }
    }
}
