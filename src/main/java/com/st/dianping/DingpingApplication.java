package com.st.dianping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.st.dianping.repository")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DingpingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingpingApplication.class, args);
    }

}
