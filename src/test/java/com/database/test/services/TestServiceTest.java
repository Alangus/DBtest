package com.database.test.services;

import com.database.test.config.TaskExecutorConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-30-12:03
 * Modified By :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class TestServiceTest {
    @Autowired TestService testService;

    @Test
    public void batchSave() throws Exception {
        long start=System.currentTimeMillis();//记录开始时间
        testService.batchSave(1000000);
        long end=System.currentTimeMillis();//记录结束时间
        long time = end-start;
        System.out.println("执行时间："+time+"ms");
        Thread.sleep(100000);
    }

}