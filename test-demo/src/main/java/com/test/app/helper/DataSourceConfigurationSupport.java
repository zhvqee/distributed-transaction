package com.test.app.helper;

import com.alibaba.druid.pool.DruidDataSource;
import com.test.app.models.DruidDataSourceProperties;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Properties;
import java.util.StringTokenizer;

public class DataSourceConfigurationSupport {
    private DataSourceConfigurationSupport() {

    }


    public static DruidDataSource dataSourceOf(DruidDataSourceProperties properties) {
        DruidDataSource dataSource = new DruidDataSource();


        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMaxActive(properties.getMaxActive());
        dataSource.setMinIdle(properties.getMinIdle());
        dataSource.setMaxWait(properties.getMaxWait());

        dataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setValidationQuery(properties.getValidationQuery());
        dataSource.setValidationQueryTimeout(properties.getValidationQueryTimeout());
        dataSource.setTestOnBorrow(properties.isTestOnBorrow());
        dataSource.setTestOnReturn(properties.isTestOnReturn());
        dataSource.setTestWhileIdle(properties.isTestWhileIdle());

        dataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());

        dataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(properties.getMaxEvictableIdleTimeMillis());

        dataSource.setPhyTimeoutMillis(properties.getPhyTimeoutMillis());

        dataSource.setRemoveAbandoned(properties.isRemoveAbandoned());
        dataSource.setRemoveAbandonedTimeout(properties.getRemoveAbandonedTimeout());

        dataSource.setConnectProperties(toProperties(properties.getConnectionProperties()));
        dataSource.setDefaultAutoCommit(properties.isDefaultAutoCommit());
        dataSource.setLogAbandoned(properties.isLogAbandoned());
        StringTokenizer tokenizer = new StringTokenizer(properties.getConnectionInitSqls(), ";");
        dataSource.setConnectionInitSqls(Collections.list(tokenizer));

        return dataSource;
    }


    private static Properties toProperties(String connectionProperties) {
        if (!StringUtils.hasText(connectionProperties)) {
            return null;
        }

        String[] arr = connectionProperties.split(";");
        if (arr.length == 0) {
            return null;
        }

        Properties properties = new Properties();

        for (String entry : arr) {
            String[] s = entry.split("=");
            String key = StringUtils.trimWhitespace(s[0]);
            if (s.length == 2) {
                String value = StringUtils.trimWhitespace(s[1]);
                properties.put(key, value);
            }

        }

        return properties;
    }

}
