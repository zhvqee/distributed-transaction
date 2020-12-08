package com.distributed.transaction.core.constants;

public class DistributedTransactionConstant {
    public static final long DEFAULT_GLOBAL_TRANSACTION_TIMEOUT = 6000;

    /**
     * 最大netty 解析数据包长度6M
     */
    public static final int MAX_FRAME_LENGTH = 6 * 1024 * 1024;


    public static final byte[] MAGIC_CODE = {(byte) 0xDB, (byte) 0xAC};

    public static final byte VERSION = 0X1;

    public static final byte[] SKIP_BYTES = {0X00, 0X00};
}
