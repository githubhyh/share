package com.dm.validate;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@PropertySource("application-dev.yml")
@EnableTransactionManagement
public class JdbcTransactionManagerConfig implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTransactionManagerConfig.class);

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource dataSource() {
        logger.info("初始化 druid DataSource");
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager dataSourceTransactionManager() {
        logger.info("initialize datasource transaction manager");
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return dataSourceTransactionManager();
    }
}
