package com.database.test.dao;

import com.database.test.beans.UserInfo;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-29-23:44
 * Modified By :
 */
public class UserDaoProvider {

    //生成批量插入SQL
    public String batchSave(Map map){
        List<UserInfo> list = (List<UserInfo>) map.get("list");
        StringBuilder sb = new StringBuilder();
//        sb.append("insert into userinfo(name) values");
//        MessageFormat mf = new MessageFormat("(#'{'list[{0}].name})");
        sb.append("insert into userinfo(name,sex,maritalStatus,age,height,");
        sb.append("weight,hobby,address,diseaseHistory,habit,exerciseTarget) values");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].name}, #'{'list[{0}].sex}," +
                "#'{'list[{0}].maritalStatus},#'{'list[{0}].age},#'{'list[{0}].height}," +
                "#'{'list[{0}].weight},#'{'list[{0}].hobby},#'{'list[{0}].address}," +
                "#'{'list[{0}].diseaseHistory},#'{'list[{0}].habit},#'{'list[{0}].exerciseTarget})");
        for (int i = 0; i < list.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
