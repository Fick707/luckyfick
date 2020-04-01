package com.fick.twocolorball;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description:
 * @Author: fick
 * @CreatedAt: 2019-05-24 23:16
 */
@SpringBootTest(classes={Application.class})
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTest {

    @Test
    public void test(){
        log.info("shah");
    }

}
