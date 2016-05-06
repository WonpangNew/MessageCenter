package main.server.servertype;

import main.server.MessageServer;

/**
 * Created by niuwanpeng on 2016/5/5.
 * Hi服务器,单例的
 */
public class HiServer extends MessageServer {
    private static volatile HiServer instance = null;

    private HiServer() {

    }
    public static HiServer getInstance() {
        if (instance == null) {
            synchronized (HiServer.class) {
                if (instance == null) {
                    instance = new HiServer();
                    instance.max = 16;
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
