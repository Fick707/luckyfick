package com.fick.luckyfick;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/3/28
 **/

@SpringBootApplication
@ImportResource(locations = "classpath:applicationContext.xml")
@Slf4j
public class Application {

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    /**
     * application start
     * @param args arguments
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        ApplicationContext application = app.run(args);
        log.info("项目启动!");

        CountDownLatch closeLatch = application.getBean(CountDownLatch.class);
        closeLatch.await();
    }
}
