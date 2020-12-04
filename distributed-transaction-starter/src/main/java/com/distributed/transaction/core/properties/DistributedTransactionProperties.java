package com.distributed.transaction.core.properties;

import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DistributedTransactionConstant.DISTRIBUTED_TRANSACTION_PREFIX)
@Data
public class DistributedTransactionProperties {

    private String applicationId;

    private String transactionServiceGroup;

    public DistributedTransactionProperties() {
        System.out.println("DistributedTransactionProperties construct");
    }
}
