package com.distributed.transaction.core.serializer;

public class SerialiazerFactory {

    public static Serializer getSerializer(String name) {
        return Iner.serializer;
    }


    static class Iner {
        private static Serializer serializer = new DefaultSerializer();
    }
}
