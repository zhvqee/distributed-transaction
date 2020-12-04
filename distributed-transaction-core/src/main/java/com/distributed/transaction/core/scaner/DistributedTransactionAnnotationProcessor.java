package com.distributed.transaction.core.scaner;

import com.distributed.transaction.core.annotation.DistributedTransactional;
import com.distributed.transaction.core.utils.CollectionUtils;
import com.distributed.transaction.core.utils.SpringProxyUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class DistributedTransactionAnnotationProcessor extends AbstractAutoProxyCreator {


    private static Set<String> PROXY_SET = new HashSet<>();

    public DistributedTransactionAnnotationProcessor() {
        System.out.println("DistributedTransactionAnnotationProcessor current is construct");
    }

    private Object[] interceptor;

    @lombok.SneakyThrows
    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        synchronized (PROXY_SET) {
            if (PROXY_SET.contains(beanName)) {
                return bean;
            }
            Class<?> serviceInterface = SpringProxyUtils.findTargetClass(bean);
            Class<?>[] interfacesIfJdk = SpringProxyUtils.findInterfaces(bean);

            if (!existsAnnotation(new Class[]{serviceInterface})
                    && !existsAnnotation(interfacesIfJdk)) {
                return bean;
            }


            if (!AopUtils.isAopProxy(bean)) {
                bean = super.wrapIfNecessary(bean, beanName, cacheKey);
            } else {
                interceptor = new Object[]{new DistributedTransactionIntercepter()};
                AdvisedSupport advised = SpringProxyUtils.getAdvisedSupport(bean);
                Advisor[] advisor = buildAdvisors(beanName, interceptor);
                for (Advisor avr : advisor) {
                    advised.addAdvisor(0, avr);
                }
            }
            PROXY_SET.add(beanName);
            return bean;
        }
    }

    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> clazz, String className, TargetSource targetSource) throws BeansException {
        return interceptor;
    }

    private boolean existsAnnotation(Class<?>[] classes) {
        if (CollectionUtils.isNotEmpty(classes)) {
            for (Class<?> clazz : classes) {
                if (clazz == null) {
                    continue;
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    DistributedTransactional annotation = method.getAnnotation(DistributedTransactional.class);
                    if (annotation != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
