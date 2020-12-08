package com.qee.test.serialzier;


import com.distributed.transaction.core.serializer.SerialiazerFactory;
import com.distributed.transaction.core.serializer.Serializer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SerializerFactoryTest {

    @Test
    public void test() {
        Serializer serializer = SerialiazerFactory.getSerializer(null);
        TestOne testOne = new TestOne();
        testOne.setAbc("abc");
        byte[] serialize = serializer.serialize(testOne);

        TestOne deserialize = serializer.deserialize(serialize);
        System.out.println(deserialize);

    }
}
