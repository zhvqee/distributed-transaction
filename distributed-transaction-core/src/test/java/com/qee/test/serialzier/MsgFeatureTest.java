package com.qee.test.serialzier;

import com.distributed.transaction.core.models.MsgFufure;
import com.distributed.transaction.core.models.RpcMessage;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class MsgFeatureTest {

    @Test
    public void test() {
        MsgFufure msgFufure = new MsgFufure();
        RpcMessage message = new RpcMessage();
        message.setRequestId(1);
        msgFufure.setRpcMessage(message);

        Object o = msgFufure.get(3000, TimeUnit.MILLISECONDS);
        System.out.println(o);
    }
}
