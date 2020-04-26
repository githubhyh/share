package com.dm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.dm.mapper")
public class SpringApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringApplication.class);
    public static void main( String[] args ) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        logger.info("spring application is running......");
    }
}
