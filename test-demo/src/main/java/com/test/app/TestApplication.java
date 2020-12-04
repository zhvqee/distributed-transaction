package com.test.app;


import com.test.app.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.test.app")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        ApplicationContext applicationContext = SpringContextProvider.getApplicationContext();
        TestService testService = applicationContext.getBean(TestService.class);
        //TestService testService = (TestService) applicationContext.getBean("testService");

        testService.test1();
        testService.test2();
        testService.test3(new TestService.B());
        testService.test3(new TestService.A());
    }
}
