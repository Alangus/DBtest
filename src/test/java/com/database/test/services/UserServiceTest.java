package com.database.test.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-29-21:08
 * Modified By :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void batchSave() throws Exception {
        //插入及查询测试
        long time1=System.currentTimeMillis();//记录开始时间
        userService.batchSave(10000);
//        userService.save(1000000);
        long time2=System.currentTimeMillis();//记录结束时间
        System.out.println("插入10万条数据执行时间："+(time2-time1)+"ms");
        userService.getUserInfoByAge(20);
        long time3 = System.currentTimeMillis();
        System.out.println("查询年龄为执行时间："+(time3-time2)+"ms");

    }

    @Test
    public void getTenBeSimilar() throws Exception{
        //进阶需求一测试
        long time1=System.currentTimeMillis();//记录开始时间
        userService.getTenBeSimilar();
        long time2=System.currentTimeMillis();//记录结束时间
        System.out.println("时间："+(time2-time1)+"ms");
    }

}