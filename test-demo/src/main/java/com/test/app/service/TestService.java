package com.test.app.service;

import com.distributed.transaction.core.annotation.DistributedTransactional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestService {

    @DistributedTransactional
    @Transactional
    public void test1() {
        System.out.println("test1");
    }

    public void test2() {
        System.out.println("test2");
    }

    @DistributedTransactional
    @Transactional
    public <T> void test3(T a) {

        System.out.println("test3 T");
    }


    public void test3(A object) {
        System.out.println("test3 obj");
    }

    public static class A {

    }

    public static class B {

    }



}
