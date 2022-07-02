package com.simmon.workserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Simmon
 */
@SpringBootApplication
@MapperScan("com.simmon.workserver.mapper")
@EnableScheduling
public class WorkServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkServerApplication.class,args);
    }
}
