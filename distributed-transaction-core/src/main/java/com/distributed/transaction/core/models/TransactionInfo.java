package com.distributed.transaction.core.models;

import lombok.Data;

@Data
public class TransactionInfo {

    private TransactionType transactionType;

    private Long timeoutMills;
}
