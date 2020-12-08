package com.qee.test.serialzier;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.constants.MsgType;
import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.transactionmanager.TransactionManagerTransportClient;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TransactionManagerTransportClientTest {


    public static ExecutorService executorService = Executors.newFixedThreadPool(6);

    @Test
    public void test() throws InterruptedException {


        for (int j = 0; j < 6; j++) {
            executorService.submit(() -> {
                TransactionManagerTransportClient client = new TransactionManagerTransportClient("127.0.0.1", 8888);
                client.connect();

                for (int i = 1; i < 500; i++) {
                    RpcMessage rpcMessage = new RpcMessage();
                    rpcMessage.setRequestId(i);
                    rpcMessage.setHeader(null);
                    rpcMessage.setHeaderLength(0);
                    rpcMessage.setVersion(DistributedTransactionConstant.VERSION);
                    rpcMessage.setMagicCode(DistributedTransactionConstant.MAGIC_CODE);
                    rpcMessage.setMsgType(MsgType.T_COMMIT.ordinal());
                    TestOne testOne = new TestOne();
                    rpcMessage.setBody(testOne);
                    client.request(rpcMessage, 4000000L, TimeUnit.MILLISECONDS);
                }
            });
        }

        Thread.sleep(40000L);

    }
}
