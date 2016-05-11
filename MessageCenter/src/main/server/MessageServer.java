package main.server;

import main.message.Message;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 控制发送数量,原子操作
     */
    protected AtomicInteger countMessage = new AtomicInteger(0);
    /**
     *一分钟时间间隔,60000毫秒
     */
    private static final int INTERVAL = 60000;
    protected static Logger logger = Logger.getLogger(MessageServer.class);

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
     */
    public void controlMessage() {
        Date date = new Date();
        Long timeLine = date.getTime() + INTERVAL;
        while (true) {
            if (date.getTime() <= timeLine && countMessage.get() < max) {
                try {
                    executor.execute(new SendMessage((Message) blockingQueue.take()));
                } catch (InterruptedException e) {
                    logger.warn("没有待处理的消息！", e);
                }
                countMessage.getAndIncrement();
            } else {
                logger.info(this.getClass().getName() + "服务器阻塞限流");
                try {
                    Thread.sleep(timeLine - date.getTime());
                } catch (InterruptedException e) {
                    logger.error("error", e);
                } finally {
                    logger.info(this.getClass().getName() + "服务器唤醒继续发送");
                    timeLine = date.getTime() + INTERVAL;
                    countMessage.set(0);
                }
            }
        }
    }
}
