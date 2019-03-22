package com.weekend.littleWeekend;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

public class test2 {
    public static void main(String[] args) {
//        for (int i = 1; i <= 100; i++){
//            if(i==10){
//                continue;
//            }
//System.out.println(i);
//    }


//        int a[]={3,4,5,6};
//        for(  int i=0;i<a.length;i++){
//            System.out.println(a[i]);
//        }
//
//        for(int entry: a ){
//
//        }
        Person p=new Person();
        p.name="张三";
        p.age=32;
        p.say();
        int i=p.a();
        //System.out.println(i);
        String b=p.getName();
        System.out.println(b);
        p.eat();



}

}