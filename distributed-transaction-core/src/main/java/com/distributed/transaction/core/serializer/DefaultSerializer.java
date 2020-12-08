package com.distributed.transaction.core.serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DefaultSerializer implements Serializer {

    private Logger logger = LoggerFactory.getLogger(DefaultSerializer.class);

    private SerializerFactory serializerFactory = new SerializerFactory();

    @Override
    public <T> byte[] serialize(T t) {
        byte[] stream = null;
        try {
            com.caucho.hessian.io.Serializer serializer = serializerFactory.getSerializer(t.getClass());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(baos);
            serializer.writeObject(t, output);
            output.close();
            stream = baos.toByteArray();
        } catch (IOException e) {
            logger.error("Hessian encode error:{}", e.getMessage(), e);
        }
        return stream;
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        T obj = null;
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes);) {
            Hessian2Input input = new Hessian2Input(is);
            obj = (T) input.readObject();
            input.close();
        } catch (IOException e) {
            logger.error("Hessian decode error:{}", e.getMessage(), e);
        }
        return obj;
    }
}
