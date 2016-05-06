package main.server.servertype;

import main.server.MessageServer;

/**
 * Created by niuwanpeng on 2016/5/5.
 * 短信服务器，单例
 */
public class ShortServer extends MessageServer {
    private static volatile ShortServer instance = null;

    private ShortServer() {

    }
    public static ShortServer getInstance() {
        if (instance == null) {
            synchronized (ShortServer.class) {
                if (instance == null) {
                    instance = new ShortServer();
                    instance.max = 20;
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
