package com.distributed.transaction.core.templates;

import com.distributed.transaction.core.models.TransactionInfo;

public interface ExecuteService {

    Object execute() throws Throwable;

    TransactionInfo getTransactionInfo();
}
