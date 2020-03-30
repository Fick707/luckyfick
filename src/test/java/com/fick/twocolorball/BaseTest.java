package com.fick.twocolorball;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description:
 * @Author: fick
 * @CreatedAt: 2019-05-24 23:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseTest {

    @Test
    public void tets(){
        System.out.printf("shah");
    }

}
