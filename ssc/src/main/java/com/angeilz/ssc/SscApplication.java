package com.angeilz.ssc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.angeilz.ssc.mapper")
@EnableScheduling
public class SscApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SscApplication.class, args);
    }

    /**
     * 如果要发布到自己的Tomcat中的时候，需要继承SpringBootServletInitializer类，并且增加如下的configure方法。
     * 如果不发布到自己的Tomcat中的时候，就无需上述的步骤
     */
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(SscApplication.class);
    }
}
