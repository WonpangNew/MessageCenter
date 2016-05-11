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

    /**
     * 单例模式，实例化后调用start方法启动该服务器的线程
     * @return
     */
    public static ShortServer getInstance() {
        if (instance == null) {
            synchronized (ShortServer.class) {
                if (instance == null) {
                    instance = new ShortServer();
                    instance.max = 15;
                    instance.start();
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
