package com.distributed.transaction.core.imports;

import com.distributed.transaction.core.annotation.EnableDistributedTransactional;
import com.distributed.transaction.core.scaner.DistributedTransactionAnnotationProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

public class StartDistributedTransactionImportSelector implements ImportSelector {

    private static final String DISTRIBUTED_TRANSACTION_PROCESSOR_NAME = DistributedTransactionAnnotationProcessor.class.getName();

    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        MultiValueMap<String, Object> annotationAttributes = annotationMetadata.getAllAnnotationAttributes(EnableDistributedTransactional.class.getName(), false);
        Boolean enable = (Boolean) annotationAttributes.getFirst("enable");
        if (enable) {
            return new String[]{DistributedTransactionAnnotationProcessorRegistrar.class.getName()};
        }
        return new String[0];
    }

    public static class DistributedTransactionAnnotationProcessorRegistrar implements ImportBeanDefinitionRegistrar {

        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(DistributedTransactionAnnotationProcessor.class).getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(DISTRIBUTED_TRANSACTION_PROCESSOR_NAME, beanDefinition);
            System.out.println("DistributedTransactionAnnotationProcessorRegistrar register DistributedTransactionAnnotationProcessor");
        }
    }
}
