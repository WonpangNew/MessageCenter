package main.demo;

import main.message.messagetype.EmailMessage;
import main.message.messagetype.HiMessage;
import main.message.messagetype.ShortMessage;
import main.messagecenter.MessageCenter;
import main.server.servertype.EmailServer;
import main.server.servertype.HiServer;
import main.server.servertype.ShortServer;

/**
 * Created by niuwanpeng on 2016/5/5.
 *
 *  1.模拟用户发送各种消息，先保存到消息中心的阻塞队列中
 *  2.由消息中心根据消息类别分发到各消息的服务器
 *  3.每个消息对应的服务器对消息分发进行限流处理
 *  4.消息中心、各个消息对应的服务器均为单例
 */
public class ClientDemo {
    public static void main(String[] args) {
        MessageCenter messageCenter = MessageCenter.getInstance();
        for (int i = 0; i < 100000; i++) {
            MessageCenter.push(new HiMessage("tomHi", "LucyHi", "Hi" + i));
            MessageCenter.push(new EmailMessage("tomEmail", "LucyEmail", "Email" + i));
            MessageCenter.push(new ShortMessage("tomShort", "LucyShort", "Short" + i));
        }
        messageCenter.start();
    }
}
