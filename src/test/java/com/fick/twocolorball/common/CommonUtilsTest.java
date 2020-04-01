package com.fick.twocolorball.common;

import com.fick.twocolorball.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: fick
 * @CreatedAt: 2019-05-24 23:19
 */
@Slf4j
public class CommonUtilsTest extends BaseTest {

    @Autowired
    StringEncryptor encryptor;

    @Test
    public void testStringEncryptor(){
        String username = encryptor.encrypt("user");
        String password = encryptor.encrypt("password");
        log.info("username:{}",username);
        log.info("password:{}",password);
    }


}
