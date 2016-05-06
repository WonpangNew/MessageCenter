package main.server.servertype;

import main.server.MessageServer;

/**
 * Created by niuwanpeng on 2016/5/5.
 * 邮件服务器，单例
 */
public class EmailServer extends MessageServer {
    private static volatile EmailServer instance = null;

    private EmailServer() {

    }
    public static EmailServer getInstance() {
        if (instance == null) {
            synchronized (EmailServer.class) {
                if (instance == null) {
                    instance = new EmailServer();
                    instance.max = 10;
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        this.controlMessage();
    }
}
