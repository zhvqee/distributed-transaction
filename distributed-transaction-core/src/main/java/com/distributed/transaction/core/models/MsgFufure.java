package com.distributed.transaction.core.models;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Data
public class MsgFufure {

    private Logger logger = LoggerFactory.getLogger(MsgFufure.class);
    private RpcMessage rpcMessage;

    private transient CompletableFuture<Object> origin = new CompletableFuture<>();


    public Object get(long timeout, TimeUnit timeUnit) {
        Object result = null;
        try {
            result = origin.get(timeout, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            logger.info("the msg get is timeout ,id:{}", rpcMessage.getRequestId());
            return null;
        }
        if (result instanceof RuntimeException) {
            logger.info("the msg get is runtime exception ,id:{},{}", rpcMessage.getRequestId(), result);
            return null;
        }
        return result;
    }

    public void setResult(Object object) {
        origin.complete(object);
    }
}
