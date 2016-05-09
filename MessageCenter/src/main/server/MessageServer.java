package main.server;

import main.message.Message;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.log4j.Logger;

/**
 * Created by niuwanpeng on 2016/5/5.
 * 消息服务器类
 */
public abstract class MessageServer extends Thread {

    protected BlockingQueue blockingQueue = new LinkedBlockingDeque();
    /**
     * 线程池，最大数目为5,可变
     */
    protected ExecutorService executor = Executors.newFixedThreadPool(5);
    /**
     * 每分钟最大发送数量,每个服务器自行设定，每个服务器都不一样
     */
    protected int max;
    /**
     * 控制发送数量countMessage<=max,而且是线程同步的
     */
    protected volatile int countMessage = 0;

    protected Logger logger = Logger.getLogger(MessageServer.class);

    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    /**
     * 将消息推送到阻塞队列
     * @param message
     */
    public void push(Message message) {
        blockingQueue.add(message);
    }

    /**
     * 控制每分钟只能发送max条信息，对服务器发送消息进行限流处理
     * 60000ms = 1分钟
     */
    public void controlMessage() {
        Date date = new Date();
        Long timeLine = date.getTime() + 60000;
        while (true) {
            if (!blockingQueue.isEmpty()) {
                if (date.getTime() <= timeLine && countMessage < max) {
                    this.sendMessage();
                    countMessage++;
                } else {
                    logger.info(this.getClass().getName() + "服务器阻塞限流");
                    try {
                        if ((timeLine - date.getTime()) > 0) {
                            Thread.sleep(timeLine - date.getTime());
                        }
                    } catch (InterruptedException e) {
                        logger.info("error", e);
                    } finally {
                        logger.info(this.getClass().getName() + "服务器唤醒继续发送");
                        timeLine = date.getTime() + 60000;
                        countMessage = 0;
                    }
                }
            }
        }
    }

    /**
     * 模拟发送消息
     * 具体实现根据Message类型的不同有不同的实现
     */
    public abstract void sendMessage();
}
