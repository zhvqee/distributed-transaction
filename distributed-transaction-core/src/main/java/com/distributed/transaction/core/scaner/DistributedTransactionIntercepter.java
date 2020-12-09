package com.distributed.transaction.core.scaner;

import com.distributed.transaction.core.annotation.DistributedTransactional;
import com.distributed.transaction.core.models.TransactionInfo;
import com.distributed.transaction.core.templates.ExecuteService;
import com.distributed.transaction.core.templates.TransactionTemplate;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DistributedTransactionIntercepter implements MethodInterceptor {

    private TransactionTemplate transactionTemplate = new TransactionTemplate();

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        DistributedTransactional distributedTransactional = null;
        if ((distributedTransactional = methodInvocation.getMethod().getAnnotation(DistributedTransactional.class)) != null) {
            System.out.println("method :" + methodInvocation.getMethod().getName() + "is invoke");
        }
        if (distributedTransactional == null) {
            return methodInvocation.proceed();
        }
        DistributedTransactional finalDistributedTransactional = distributedTransactional;
        return transactionTemplate.doTranscation(new ExecuteService() {
            @Override
            public Object execute() throws Throwable {
                Object result = null;

                //1、start global transaction

                try {
                    //2、execute bussiness
                    result = methodInvocation.proceed();
                } catch (Throwable e) {
                    // rollback global transaction
                    return null;
                }

                //3.commit global transaction


                return result;
            }

            @Override
            public TransactionInfo getTransactionInfo() {
                TransactionInfo transactionInfo = new TransactionInfo();
                transactionInfo.setTimeoutMills(finalDistributedTransactional.timeoutMills());
                transactionInfo.setTransactionType(finalDistributedTransactional.type());
                return transactionInfo;
            }
        });
    }
}
