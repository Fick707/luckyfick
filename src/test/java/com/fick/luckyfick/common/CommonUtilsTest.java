package com.fick.luckyfick.common;

import com.fick.common.utils.file.CommonFileWriter;
import com.fick.luckyfick.BaseTest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

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

    @Test
    public void testWriteFile(){
        CommonFileWriter fileWriter = new CommonFileWriter("C:/Users/fick7/Desktop/test.log");
        fileWriter.append("test2\n");
    }

    @Test
    public void testMin(){
        List<Integer> ints = Lists.newArrayList(1,3,89,0);
        System.out.printf("min:"+ints.stream().mapToInt(Integer::intValue).min().getAsInt());
    }

}
