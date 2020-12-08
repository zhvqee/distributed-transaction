package com.distributed.transaction.core.serializer;

public interface Serializer {


    <T> byte[] serialize(T t);

    <T> T deserialize(byte[] bytes);
}
