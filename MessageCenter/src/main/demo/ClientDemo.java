package main.demo;

import main.messagecenter.MessageCenter;

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
        MessageDemo messageDemo = new MessageDemo();
        /**
         * 模拟发送消息的线程
         */
        messageDemo.start();
        /**
         * 模拟消息中心分发消息
         */
        messageCenter.start();
    }
}
