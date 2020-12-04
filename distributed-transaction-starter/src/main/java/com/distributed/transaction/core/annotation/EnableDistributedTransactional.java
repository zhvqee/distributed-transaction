package com.distributed.transaction.core.annotation;

import com.distributed.transaction.core.imports.StartDistributedTransactionImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(StartDistributedTransactionImportSelector.class)
public @interface EnableDistributedTransactional {
    boolean enable() default true;

}
