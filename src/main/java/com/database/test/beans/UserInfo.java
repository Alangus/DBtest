package com.database.test.beans;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-29-17:57
 * Modified By :
 */
public class UserInfo {
    private static ThreadLocalRandom random=ThreadLocalRandom.current();
    private int id;//主键
    private String name;//姓名 str12
    private boolean sex; //性别 true:男 false:女
    private boolean maritalStatus; //婚否 true:已婚 false:未婚
    private float age; //年龄 0-150
    private float height;//身高 0-200
    private float weight;//体重 0-200
    private String hobby;//爱好 str60
    private String address;//家庭住址 str60
    private String diseaseHistory; //既往疾病史 str200
    private String habit;//生活习惯 str200
    private String exerciseTarget;//运动目标 str60

    public static Random getRandom() {
        return random;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSex() {
        return sex;
    }

    public boolean isMaritalStatus() {
        return maritalStatus;
    }

    public float getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getHobby() {
        return hobby;
    }

    public String getAddress() {
        return address;
    }

    public String getDiseaseHistory() {
        return diseaseHistory;
    }

    public String getHabit() {
        return habit;
    }

    public String getExerciseTarget() {
        return exerciseTarget;
    }

    public UserInfo(){
        this.name = getStr(3);
        this.sex = getBool();
        this.maritalStatus = getBool();
        this.age = getFloat(150);
        this.height = getFloat(200);
        this.weight = getFloat(200);
        this.hobby = getStr(10);
        this.address = getStr(10);
        this.diseaseHistory = getStr(30);
        this.habit = getStr(30);
        this.exerciseTarget = getStr(10);
    }

    //生成长度为length的随机中文字符串
    public String getStr(int length){
        StringBuilder str = new StringBuilder();
        for (int i = 0;i<length;i++){
//            //获取随机中文字符
//            int highCode = 176+Math.abs(random.nextInt(39));
//            int lowCode = 161+ Math.abs(random.nextInt(93));
//            byte[] b = new byte[2];
//            b[0] = (Integer.valueOf(highCode)).byteValue();
//            b[1] = (Integer.valueOf(lowCode)).byteValue();
//            try {
//                String s = new String(b, "GBK");
//                String s2 = new String(s.getBytes("ISO-8859-1"),"UTF-8");
//                str.append(s2) ;
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            //获取随机中文字符
            int a = 0x9fa5 - 0x4e00;
            int code = 0x4e00 + random.nextInt(a);
            str.append((char)code);
        }
        return str.toString();
    }

    //获取随机布尔值
    public boolean getBool(){
        return random.nextBoolean();
    }

    //获取随机0~max范围内值
    public float getFloat(int max){
        return (float)random.nextInt(max);
    }

    //计算10个点的差异值
    public static float getDistance(List<UserInfo> list){
        float sex = 0;
        float age = 0;
        float height = 0;
        float weight = 0;
        int size = list.size();
        float distance = 0;
        for (UserInfo userInfo:list){
            if (userInfo.isSex()) sex+=1;
            age+=userInfo.getAge();
            height+=userInfo.getHeight();
            weight+=userInfo.getWeight();
        }
        sex = sex/size;
        age = age/size;
        height = height/size;
        weight = weight/size;
        for (UserInfo userInfo:list){
            if (userInfo.isSex()) distance +=600*Math.abs(sex-1);
            else distance += 600*Math.abs(sex-0);
            distance += 4*Math.abs(age-userInfo.getAge());
            distance += 3*Math.abs(height-userInfo.getHeight());
            distance += 3*Math.abs(weight-userInfo.getWeight());
        }
        return distance;
    }

    public static String toString(List<UserInfo> list){
        StringBuilder sb = new StringBuilder();
        for (UserInfo u:list){
            sb.append("{id:"+u.getId()+",");
            sb.append("name:"+u.getName()+",");
            sb.append("sex:"+u.isSex()+",");
            sb.append("maritalStatus:"+u.isMaritalStatus()+",");
            sb.append("age:"+u.getAge()+",");
            sb.append("height:"+u.getHeight()+",");
            sb.append("weight:"+u.getWeight()+",");
            sb.append("hobby:"+u.getHobby()+",");
            sb.append("address:"+u.getAddress()+",");
            sb.append("diseaseHistory:"+u.getDiseaseHistory()+",");
            sb.append("habit:"+u.getHabit()+",");
            sb.append("exerciseTarget:"+u.getExerciseTarget()+"}\n");
        }
        return sb.toString();
    }
}
