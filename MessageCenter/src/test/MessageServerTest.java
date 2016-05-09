package test;

import main.message.messagetype.HiMessage;
import main.server.MessageServer;
import main.server.servertype.HiServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by niuwanpeng on 2016/5/6.
 */
public class MessageServerTest {
    private MessageServer messageServer = HiServer.getInstance();

    @Before
    public void addMeaaage() {
        for (int i = 0; i < 40; i++) {
            messageServer.push(new HiMessage("test", "test2", "test" + i));
        }
    }

    @Test
    public void testControlMessage() {
        Date date = new Date();
        Long timeLine = date.getTime() + 60000;
        int m = 0;
        while (!messageServer.getBlockingQueue().isEmpty()) {
            if (date.getTime() <= timeLine && m < 16) {
                try {
                    Thread.sleep(2000);
                    messageServer.getBlockingQueue().poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                m++;
            } else {
                break;
            }
        }
        Assert.assertEquals(16, m);
    }
}
