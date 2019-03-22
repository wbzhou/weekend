package com.weekend.rrr;

import java.util.ArrayList;

/**
 * Created by weekend on 2019/2/22.
 */
public class Test1 {
    public static void main (String[] args){
        ArrayList arrayList =new ArrayList();
        Array a =new Array();
//        arrayList.add("cathy");
//        arrayList.add("cathy");
//        arrayList.add("cathy1");
//        System.out.println(arrayList.size());
//       for (int i=0;i<arrayList.size();i++){
//           String name=(String) arrayList.get(i);
//           System.out.println(name);
//       }
        Dog dog =new Dog("大黄","萨摩");
        Dog dog1 =new Dog("小白","泰迪");
        arrayList.add(dog);
        arrayList.add(dog1);
        System.out.println(arrayList.size());
        for(int i=0;i<arrayList.size();i++){
           Dog d= (Dog)arrayList.get(i);
            System.out.println(d.getName()+"\t"+d.getStrain());

        }
        int [] arr={1,3,5,6};
        a.find(arr);

  }




}
