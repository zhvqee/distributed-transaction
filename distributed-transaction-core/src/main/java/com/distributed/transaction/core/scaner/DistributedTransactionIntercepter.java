package com.distributed.transaction.core.scaner;

import com.distributed.transaction.core.annotation.DistributedTransactional;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DistributedTransactionIntercepter implements MethodInterceptor {
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation.getMethod().getAnnotation(DistributedTransactional.class) != null) {
            System.out.println("method :" + methodInvocation.getMethod().getName() + "is invoke");
        }
        return methodInvocation.proceed();
    }
}
