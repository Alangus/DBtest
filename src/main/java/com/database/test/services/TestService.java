package com.database.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-30-12:00
 * Modified By :
 */
@Service
public class TestService {
    @Autowired
    private UserService userService;

    public void batchSave(int number){
        //使用多线程
        final int temp = number/10;
        for (int i = 0;i<10;i++){
            int num ;
            if (i==9) num= number - temp*9;
            else num = temp;
            userService.saveByThread(num,i);
        }
    }

}
