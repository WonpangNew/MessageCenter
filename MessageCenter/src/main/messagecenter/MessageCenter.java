package main.messagecenter;

import main.message.Message;
import main.server.servertype.EmailServer;
import main.server.servertype.HiServer;
import main.server.servertype.ShortServer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * Created by niuwanpeng on 2016/5/5.
 *
 * 单例的
 */
public class MessageCenter extends Thread {
    private static BlockingQueue<Message> blockingQueue = new LinkedBlockingDeque();

    private static  volatile MessageCenter instance = null;

    private MessageCenter() {

    }

    public static MessageCenter getInstance() {
        if (instance == null) {
            synchronized (MessageCenter.class) {
                if (instance == null) {
                    instance = new MessageCenter();
                }
            }
        }
        return instance;
    }

    public static BlockingQueue<Message> getBlockingQueue() {
        return blockingQueue;
    }

    public static void push(Message message) {
        blockingQueue.add(message);
    }

    /**
     * 根据消息类型分发到各个服务器
     */
    public static void dispatchMessage() {
        Message message = blockingQueue.poll();
        String type = message.getClass().getName();
        if ("main.message.messageType.HiMessage".equals(type)) {
            HiServer.getInstance().push(message);
        }
        else if ("main.message.messageType.ShortMessage".equals(type)) {
            ShortServer.getInstance().push(message);
        }
        else if ("main.message.messageType.EmailMessage".equals(type)) {
            EmailServer.getInstance().push(message);
        }
        else {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        while (!blockingQueue.isEmpty()) {
            MessageCenter.dispatchMessage();
        }
    }
}
