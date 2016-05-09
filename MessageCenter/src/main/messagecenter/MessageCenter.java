package main.messagecenter;

import main.message.Message;
import main.server.MessageServer;
import main.server.servertype.EmailServer;
import main.server.servertype.HiServer;
import main.server.servertype.ShortServer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * Created by niuwanpeng on 2016/5/5.
 * 单例的
 * blockingQueue  阻塞队列
 * map 键值对 <消息类型，消息对应的服务器实例> ，主要用来分发消息
 *
 */
public class MessageCenter extends Thread {
    private static BlockingQueue<Message> blockingQueue = new LinkedBlockingDeque();
    private static volatile MessageCenter instance = null;
    private static Map<String, MessageServer> map = new HashMap<>();

    private MessageCenter() {

    }

    /**
     *单例模式 ，双重检查锁
     * @return
     */
    public static MessageCenter getInstance() {
        if (instance == null) {
            synchronized (MessageCenter.class) {
                if (instance == null) {
                    instance = new MessageCenter();
                    instance.setMap();
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
     * 初始化map映射信息
     */
    public static void setMap() {
        map.put("main.message.messagetype.HiMessage", HiServer.getInstance());
        map.put("main.message.messagetype.ShortMessage", ShortServer.getInstance());
        map.put("main.message.messagetype.EmailMessage", EmailServer.getInstance());
    }

    /**
     * 提供注册接口，当增加新的消息类型时注册到map容器中
     * @param typePath
     * @param messageServer
     */
    public static void register(String typePath, MessageServer messageServer) {
        map.put(typePath, messageServer);
    }

    /**
     * 根据消息类型分发到各个服务器
     */
    public static void dispatchMessage() {
        Message message = blockingQueue.poll();
        String type = message.getType();
        MessageServer messageServer = map.get(type);
        messageServer.push(message);
    }

    @Override
    public void run() {
        while (true) {
            if (!blockingQueue.isEmpty()) {
                MessageCenter.dispatchMessage();
            }
        }
    }
}
