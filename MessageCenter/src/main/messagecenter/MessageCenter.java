package main.messagecenter;

import main.message.Message;
import main.message.MessageType;
import main.server.MessageServer;
import main.server.servertype.EmailServer;
import main.server.servertype.HiServer;
import main.server.servertype.ShortServer;
import org.apache.log4j.Logger;
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
    public static Logger logger = Logger.getLogger(MessageCenter.class);

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
        map.put(MessageType.Hi.toString(), HiServer.getInstance());
        map.put(MessageType.SHORTMESSAGE.toString(), ShortServer.getInstance());
        map.put(MessageType.EMAIL.toString(), EmailServer.getInstance());
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
    public static void dispatchMessage(Message message) {
        String type = message.getType();
        MessageServer messageServer = map.get(type);
        messageServer.push(message);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = blockingQueue.take();
                MessageCenter.dispatchMessage(message);
            } catch (InterruptedException e) {
                logger.info("没有待处理消息", e);
            }
        }
    }
}
