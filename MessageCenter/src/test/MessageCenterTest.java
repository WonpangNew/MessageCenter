package test;

import main.messagecenter.MessageCenter;
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
}
