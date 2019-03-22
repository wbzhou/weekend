package com.weekend.rrr;

/**
 * Created by weekend on 2019/2/28.
 */
public class Test2 {
    public static void main (String[] args){
        Test2 test =new Test2();
        test.b();
    }
    public static void a(){
        System.out.println("adfgg");

    }
    public void b(){
        a();
        System.out.println("dfggg");

    }
}
