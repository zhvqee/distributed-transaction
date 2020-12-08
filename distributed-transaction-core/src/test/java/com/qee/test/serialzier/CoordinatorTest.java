package com.qee.test.serialzier;

import com.distributed.transaction.core.coordinator.Coordinator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoordinatorTest {

    @Test
    public void xtest() {
        Coordinator coordinator = new Coordinator();
        coordinator.bind();

        System.out.println("=====");

    }
}
