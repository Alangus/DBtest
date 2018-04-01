package com.database.test.controller;

import com.database.test.beans.UserInfo;
import com.database.test.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-30-17:37
 * Modified By :
 */
@RestController
@RequestMapping(value = "user")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    //使用逐条插入的方式进行插入，参数为插入数量
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String save(@RequestParam(value = "number") int number) {
        long time1=System.currentTimeMillis();//记录开始时间
        userService.save(number);
        long time2=System.currentTimeMillis();//记录结束时间
        logger.info("插入"+number+"条数据执行时间："+(time2-time1)+"ms");
        System.out.println("插入"+number+"条数据执行时间："+(time2-time1)+"ms");
        return (time2-time1)+"ms";
    }

    //使用批量插入的方式进行插入，每1000条数据拼接为一条SQL插入一次，参数为插入数量
    @RequestMapping(value = "/batchSave",method = RequestMethod.GET)
    public String batchSave(@RequestParam(value = "number") int number) {
        long start=System.currentTimeMillis();//记录开始时间
        userService.batchSave(number);
        long end=System.currentTimeMillis();//记录结束时间
        logger.info("插入"+number+"条数据执行时间："+(end-start)+"ms");
        System.out.println("插入"+number+"条数据执行时间："+(end-start)+"ms");
        return (end-start)+"ms";
    }

    //查询年龄为age的数据
    @RequestMapping(value = "/getUserInfoByAge",method = RequestMethod.GET)
    public String getUserInfoByAge(@RequestParam(value = "age") int age){
        long time1=System.currentTimeMillis();//记录开始时间
        userService.getUserInfoByAge(age);
        long time2=System.currentTimeMillis();//记录结束时间
        logger.info("查询执行时间："+(time2-time1)+"ms");
        System.out.println("查询执行时间："+(time2-time1)+"ms");
        return (time2-time1)+"ms";
    }

    //进阶一
    @RequestMapping(value = "/getTenBeSimilar",method = RequestMethod.GET)
    public List<UserInfo> getTenBeSimilar() {
        long time1=System.currentTimeMillis();//记录开始时间
        List<UserInfo> list = userService.getTenBeSimilar();
        long time2=System.currentTimeMillis();//记录结束时间
        logger.info("进阶执行时间："+(time2-time1)+"ms");
        System.out.println("进阶执行时间："+(time2-time1)+"ms");
        return list;
    }

    //测试web项目是否正常运行
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        logger.info("hello");
        return "hello";
    }

}
