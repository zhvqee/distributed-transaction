package com.distributed.transaction.core.autoconfiguration;

import com.distributed.transaction.core.annotation.EnableDistributedTransactional;
import com.distributed.transaction.core.constants.DistributedTransactionConstant;
import com.distributed.transaction.core.properties.DistributedTransactionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = DistributedTransactionConstant.DISTRIBUTED_TRANSACTION_PREFIX,
        name = DistributedTransactionConstant.ENABLE,
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(DistributedTransactionProperties.class)
@EnableDistributedTransactional
public class DistributedTransactionAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedTransactionAutoConfiguration.class);

}
