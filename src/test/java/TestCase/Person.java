package com.weekend.littleWeekend;

public class Person {
    public String name;
    public int age;

    public void say(){
        System.out.println(name+"shuihua"+age);
    }
    public void run(){
        drink();
        System.out.println("喝果汁");
    }
    public void drink(){
        System.out.println("come on");
    }
    public int a(){
        return 7;
    }
    public String getName(){
        return name;
    }
    public void eat(){
        try {
            System.out.println(2/0);
        }catch (Exception e){
            System.out.println("代码异常");
        }finally {
            System.out.println("完毕");
        }
    }
}
