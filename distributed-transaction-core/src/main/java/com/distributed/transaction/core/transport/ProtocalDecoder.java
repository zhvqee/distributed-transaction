package com.distributed.transaction.core.transport;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.exceptions.DTResolvingException;
import com.distributed.transaction.core.models.RpcMessage;
import com.distributed.transaction.core.serializer.SerialiazerFactory;
import com.distributed.transaction.core.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 消息协议 直接采用 seata 内容
 * * 0     1     2     3     4     5     6     7     8     9    10     11    12    13    14    15    16
 * * +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
 * * |   magic   |Proto|     Full length       |    Head               | Msg |          RequestId    |
 * * |   code    |colVer|    (head+body)      |   Length               |Type  |                       |
 * * +-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
 * * |                                                                                               |
 * * |                                   Head Map [Optional]                                         |
 * * +-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
 * * |                                                                                               |
 * * |                                         body                                                  |
 * * |                                                                                               |
 * * |                                        ... ...                                                |
 * * +-----------------------------------------------------------------------------------------------+
 */

public class ProtocalDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocalDecoder(int maxFrameLength) {
        super(maxFrameLength, 3, 4, -7, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        if (decode instanceof ByteBuf) {
            ByteBuf readByteBuf = (ByteBuf) decode;
            byte magicCode1 = readByteBuf.readByte();
            byte magicCode2 = readByteBuf.readByte();
            if (magicCode1 != DistributedTransactionConstant.MAGIC_CODE[0]
                    || magicCode2 != DistributedTransactionConstant.MAGIC_CODE[1]) {
                throw new DTResolvingException("不识别该字节流");
            }
            byte version = readByteBuf.readByte();
            if (version != DistributedTransactionConstant.VERSION) {
                throw new DTResolvingException("当前版本不匹配");
            }

            int fullLength = readByteBuf.readInt();
            int headerLength = readByteBuf.readInt();
            byte msgType = readByteBuf.readByte();

            int requestId = readByteBuf.readInt();

            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setMagicCode(DistributedTransactionConstant.MAGIC_CODE);
            rpcMessage.setVersion(version);
            rpcMessage.setFullLength(fullLength);
            rpcMessage.setHeaderLength(headerLength);
            rpcMessage.setMsgType(msgType);
            rpcMessage.setRequestId(requestId);

            if (headerLength != 0) {
                throw new DTResolvingException("头部解析目前不支持");
            }

            int bodyLength = fullLength - headerLength-16;
            byte[] bodybytes = new byte[bodyLength];
            readByteBuf.readBytes(bodybytes);

            // 目前默认
            Serializer serializer = SerialiazerFactory.getSerializer(null);
            rpcMessage.setBody(serializer.deserialize(bodybytes));
            return rpcMessage;
        }
        return decode;
    }
}
