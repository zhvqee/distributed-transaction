package com.distributed.transaction.core.annotation;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.models.TransactionType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DistributedTransactional {

    TransactionType type() default TransactionType.AT;

    long timeoutMills() default DistributedTransactionConstant.DEFAULT_GLOBAL_TRANSACTION_TIMEOUT;

}
