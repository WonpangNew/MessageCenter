package main.server;

import main.message.Message;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by niuwanpeng on 2016/5/5.
 * 消息服务器类
 */
public abstract class MessageServer extends Thread {

    protected BlockingQueue blockingQueue = new LinkedBlockingDeque();
    protected int max;

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
     * 控制每分钟只能发送M条信息，对服务器发送消息进行限流处理
     * 60000ms = 1分钟
     */
    public void controlMessage() {
        Date date = new Date();
        Long timeLine = date.getTime() + 60000;
        int m = 0;
        while (!blockingQueue.isEmpty()) {
            if (date.getTime() <= timeLine && m < max) {
                try {
                    Thread.sleep(2000);
                    System.out.println(blockingQueue.poll().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m++;
            } else {
                System.out.println(this.getClass().getName() + "服务器阻塞");
                try {
                    /**
                     *  阻塞后模拟服务器停滞时间
                     */
                    Thread.sleep(timeLine - date.getTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeLine = date.getTime() + 60000;
                m = 0;
            }
        }
    }

    public void sendMessage(){

    }
}
