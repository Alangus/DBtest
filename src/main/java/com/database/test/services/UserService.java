package com.database.test.services;

import com.database.test.beans.UserInfo;
import com.database.test.beans.UserPartInfo;
import com.database.test.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-29-21:01
 * Modified By :
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public void save(int number){
        //逐条插入数据
        for (int i = 0; i < number; i++){
            userDao.save(new UserInfo());
        }
    }

    public void batchSave(int number){
        //批量插入每生成1000条数据插入一次
        int page = number/1000+1;
        for (int i = 0;i<page;i++){
            int rows =1000;
            List<UserInfo> list=  new ArrayList<UserInfo>();
            if (i==page-1) rows = number - i*1000;
            for (int j = 0; j<rows; j++){
                list.add(new UserInfo());
            }
            if (list.size()!=0)
                userDao.batchSave(list);
        }
    }

    public void batchProduce(int number){
        //测试生成数据时间
        int page = number/1000;
        for (int i = 0;i<page;i++){
            int rows =1000;
            List<UserInfo> list=  new ArrayList<UserInfo>();
            if (i==page-1) rows = number - i*1000;
            for (int j = 0; j<rows; j++){
                list.add(new UserInfo());
            }
        }
    }

    @Async("MyAsync")
    public void saveByThread(int number,int i){
        //尝试使用多线程插入未成功
        System.out.println("开始"+i);
//        userService.batchSave(number);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束："+i);
    }

    //根据年龄查询用户
    public List<UserInfo> getUserInfoByAge(int age){
        List<UserInfo> list =userDao.listUserInfo(age);
        return list;
    }

    //在用户记录中查出年龄、性别、身高、体重最接近的10条记录
    public List<UserInfo> getTenBeSimilar(){
        /**参考K-means算法
         * 1.查询出数据库中所有数据，对查出的N条数据进行遍历，按距离是否小于3进行分组
         * 2.若分组后组数大于N/10，则对数据按距离是否小于6，9，12...2400进行分组，直到组数K小于等于N/10
         * 3.对K组数据中每组数据求平均值
         * 3.以这K个数作为聚类中心，使用K-means算法，得到稳定的聚类中心
         * 4.找出聚类中心附近最近的10条数据，计算10个点到他们中心距离之和，比较，找出最小的一组数据
         * */
        List<UserPartInfo> list = userDao.groupUserInfo(); //查询出的原始数据
        List<UserPartInfo> groupList = new ArrayList<>(); //分类后计算出的中心点
        //循环得到初始的K个中心点
        for (int i = 3;i<=2400;i+=3){
            //对距离小于i的类进行合并
            groupList = mergeList(list,i);
            if (groupList.size()>0&&groupList.size()<=1000) break;
        }
        //对list中每个UserPartInfo求其记录的List<UserPartInfo>的平均值,初始聚类中心点
        for (UserPartInfo u:groupList){
            u.average();
        }
        //使用K-means算法，得到稳定的聚类中心
        while(true){
            if (classifyData(list,groupList)) break;
        }
        List<UserInfo> result = compareDistance(groupList);
        logger.info("\n"+UserInfo.toString(result));
        return result;
    }

    //找出list中点个数大于10的中心点，查出每个点附近最接近的10条数据，
    // 比较这10条数据差异值，选择最小的返回
    private List<UserInfo> compareDistance(List<UserPartInfo> list){
        List<UserInfo> result = null;
        float distance=Float.MAX_VALUE;
        for (int i=0;i<list.size();i++){
            List<UserInfo> userInfos = userDao.listTenUser(list.get(i));
            float temp = UserInfo.getDistance(userInfos);
            if (temp<distance){
                distance = temp;
                result = userInfos;
            }
        }
        logger.info("10条最相似数据差异值为："+distance);
        return result;
    }

    //对list进行遍历，将差异值小于x的进行合并
    public List<UserPartInfo> mergeList(List<UserPartInfo> list,int x){
        List<UserPartInfo> newList = new ArrayList<>();
        for (UserPartInfo userPartInfo:list){
            userPartInfo.clearList();
            UserPartInfo newu = new UserPartInfo(userPartInfo);//复制一个UserPartInfo
            boolean flag = true;
            if (newList.size()==0){
                newList.add(newu);
                continue;
            }
            float distance = Float.MAX_VALUE;
            UserPartInfo u = null;
            //计算要分组的点到每个中心点的距离，若存在小于x的则找到距离最小的加入
            for (UserPartInfo newUserPartInfo:newList){
                float temp = userPartInfo.getDistance(newUserPartInfo);
                if (temp<=x&&temp<distance){
                    distance = temp;
                    u = newUserPartInfo;
                    flag = false;
                }
            }
            if (flag) newList.add(newu);
            else u.addList(newu);
        }
        return newList;
    }

    //对数据进行分类，得到新的聚类中心
    public boolean classifyData(List<UserPartInfo> list,List<UserPartInfo> groupList){
        //对原始数据按照离中心点的距离分类
        for (UserPartInfo u:list){
            float distance = Float.MAX_VALUE;
            UserPartInfo us = null;
            for (UserPartInfo gu:groupList){
                float temp = u.getDistance(gu);
                if (temp<distance){
                    distance =temp;
                    us = gu;
                }
            }
            us.addList(u);
        }
        //对分类后的数据求平均值，得到新的中心点
        boolean flag = true;
        for (UserPartInfo us:groupList){
            if (!us.average2()) flag = false;
        }
        return flag;
    }
}
