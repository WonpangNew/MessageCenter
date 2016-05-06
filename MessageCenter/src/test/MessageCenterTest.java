package test;

import main.message.messagetype.EmailMessage;
import main.message.messagetype.HiMessage;
import main.message.messagetype.ShortMessage;
import main.messagecenter.MessageCenter;
import main.server.servertype.EmailServer;
import main.server.servertype.HiServer;
import main.server.servertype.ShortServer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by niuwanpeng on 2016/5/6.
 */
public class MessageCenterTest {
    private  MessageCenter messageCenter = MessageCenter.getInstance();

    @Test
    public void testGetInstance() {
        Assert.assertEquals(messageCenter, MessageCenter.getInstance());
    }

    @Test
    public void testDispatchMessage() {
        MessageCenter.push(new HiMessage("tomHi", "LucyHi", "Hi"));
        MessageCenter.push(new EmailMessage("tomEmail", "LucyEmail", "Email"));
        MessageCenter.push(new ShortMessage("tomShort", "LucyShort", "Short"));
        while (!messageCenter.getBlockingQueue().isEmpty()) {
            messageCenter.dispatchMessage();
        }
        Assert.assertEquals(false, HiServer.getInstance().getBlockingQueue().isEmpty());
        Assert.assertEquals(false, EmailServer.getInstance().getBlockingQueue().isEmpty());
        Assert.assertEquals(false, ShortServer.getInstance().getBlockingQueue().isEmpty());
    }
}
