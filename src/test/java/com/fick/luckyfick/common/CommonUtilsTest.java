package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

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
        String username = encryptor.encrypt("DmsYdfPGB5cdGBcJ2RVjQPCx0ppGDSeATbXTErG1");
        String password = encryptor.encrypt("P-Mt7XhCjS-8okNnLQAzoGFT3NU3kB7niYcAP1kJ8zD89cMq3XQgCe-lcrUrAL2P");
        log.info("username:{}",username);
        log.info("password:{}",password);
    }

    @Test
    public void testCalender(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        System.out.println(c.get(Calendar.YEAR) * 1000 + 1);
    }

}
