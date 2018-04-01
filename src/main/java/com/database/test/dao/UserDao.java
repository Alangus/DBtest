package com.database.test.dao;

import com.database.test.beans.UserInfo;
import com.database.test.beans.UserPartInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-29-20:23
 * Modified By :
 */
@Mapper
public interface UserDao {

    //单条插入
    @Insert("insert into userinfo(name,sex,maritalStatus,age,height,weight,hobby,address," +
            "diseaseHistory,habit,exerciseTarget) values(#{name},#{sex},#{maritalStatus}," +
            "#{age},#{height},#{weight},#{hobby},#{address},#{diseaseHistory},#{habit},#{exerciseTarget})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(UserInfo userInfo);

    //批量插入，预编译SQL，一次性将list中数据全部插入
    @InsertProvider(type = UserDaoProvider.class, method = "batchSave")
    void batchSave(@Param("list") List<UserInfo> list);

    //查询出年龄为age的数据
    @Select("select * from userinfo where age = #{age}")
    List<UserInfo> listUserInfo(@Param("age") int age);

    //将数据按照性别年龄身高体重进行分类
//    @Select("select sex,age,height,weight,COUNT(id) as num from userinfo " +
//            "group by sex,age,height,weight order by num DESC")
    @Select("select id,sex,age,height,weight from userinfo order by id")
    List<UserPartInfo> groupUserInfo();

    @Select("select id,sex,age,height,weight as distance from userinfo order by 600*ABS(#{sex}-sex)" +
            "+4*ABS(#{age}-age)+3*ABS(#{height}-height)+3*ABS(#{weight}-weight) LIMIT 10")
    List<UserInfo> listTenUser(UserPartInfo userPartInfo);
}
