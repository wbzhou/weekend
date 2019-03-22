package com.weekend.littleWeekend;

import com.littleWeekend.Application;
import com.littleWeekend.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



@SpringBootTest(classes = { Application.class })
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestNgCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private IActivityService IActivityService;
    @Test(groups = "gOne")
    public void test1(){
        String msg= IActivityService.checkUse();
        System.out.println("这里的gOne:"+msg);
    }

    @Test(groups = "gTwo")
    public void test2(){
        System.out.println("我是gTwo");
    }

    @Parameters({"a","b"})
    @Test(groups = "gOneTwo")
    public void test3(int a , int b){
        System.out.println("这里的分组信息:"+a+b);
    }

    @Test(groups="interface1")
    public void testActivity(){
        String res= IActivityService.checkActivity("123");
        System.out.println("测试结果:"+res);
    }
}
