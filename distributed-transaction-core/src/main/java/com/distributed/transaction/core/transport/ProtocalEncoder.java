package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.serializer.SerialiazerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocalEncoder extends MessageToByteEncoder<RpcMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        if (msg == null) {
            return;
        }
        Object body = msg.getBody();
        byte[] bodybytes = SerialiazerFactory.getSerializer(null).serialize(body);

        out.writeByte(DistributedTransactionConstant.MAGIC_CODE[0]);
        out.writeByte(DistributedTransactionConstant.MAGIC_CODE[1]);
        out.writeByte(DistributedTransactionConstant.VERSION);
        out.writeInt(bodybytes.length + 16);
        out.writeInt(0);
        out.writeByte(msg.getMsgType());
        out.writeInt(msg.getRequestId());
        out.writeBytes(bodybytes);
    }
}
