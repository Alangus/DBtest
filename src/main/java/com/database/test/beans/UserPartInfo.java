package com.database.test.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author : alan
 * Description:
 * User: alan
 * Date: Created in 2018-03-31-20:15
 * Modified By :
 */
public class UserPartInfo {
    private float sex;//性别 0-1
    private float age; //年龄 0-150
    private float height;//身高 0-200
    private float weight;//体重 0-200
    private List<UserPartInfo> list = new ArrayList<>();//用于归类时记录该中心点下其他点信息

    public UserPartInfo(){

    }

    public UserPartInfo(UserPartInfo u){
        this.sex = u.sex;
        this.age = u.age;
        this.height = u.height;
        this.weight  = u.weight;
        this.list = new ArrayList<>();
    }

    public float getSex() {
        return sex;
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


    public List<UserPartInfo> getList() {
        return list;
    }

    public void addList(UserPartInfo o) {
        this.list.add(o);
    }


    public float getDistance(UserPartInfo o){
        return 600*Math.abs(this.getSex()-o.getSex())
                +4*Math.abs(this.getAge()-o.getAge())
                +3*Math.abs(this.getHeight()-o.getHeight())
                +3*Math.abs(this.getWeight()-o.getWeight());
    }

    public void average(){
        if (this.list.size()==0) return;
        float sex = this.sex;
        float age = this.age;
        float height = this.height;
        float weight = this.weight;
        int num = list.size()+1;
        for (UserPartInfo userPartInfo:this.list){
            sex+=userPartInfo.getSex();
            age+=userPartInfo.getAge();
            height+=userPartInfo.getHeight();
            weight+=userPartInfo.getWeight();
        }
        this.sex = sex/num;
        this.age = age/num;
        this.height = height/num;
        this.weight = weight/num;
        this.list = new ArrayList<>();
    }

    public void clearList(){
        this.list = new ArrayList<>();
    }

    public boolean average2(){
        if (this.list.size()==0) return true;
        float sex = 0;
        float age = 0;
        float height = 0;
        float weight = 0;
        int num = list.size();
        for (UserPartInfo userPartInfo:this.list){
            sex+=userPartInfo.getSex();
            age+=userPartInfo.getAge();
            height+=userPartInfo.getHeight();
            weight+=userPartInfo.getWeight();
        }
        sex = sex/num;
        age = age/num;
        height = height/num;
        weight = weight/num;
        this.list = new ArrayList<>();
        if (sex==this.sex&&age==this.age&&height==this.height&&weight==this.weight) {
            return true;
        }else {
            this.sex = sex;
            this.age = age;
            this.height = height;
            this.weight = weight;
            return false;
        }
    }

}
