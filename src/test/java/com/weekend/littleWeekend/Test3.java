package com.weekend.littleWeekend;

import com.littleWeekend.domain.TestEntry;
import com.littleWeekend.util.ExcelUtil;

import java.util.List;

/**
 * Created by weekend on 2019/3/9.
 */

public class Test3 {
    public static void main (String[] tt){
//		System.out.println("佛挡杀佛");
        String excelFile="/Users/weekend/zwb/doc/test1.xls";
        String sheetName="Sheet1";

        ExcelUtil ins=new ExcelUtil();
        try {

            List<TestEntry> list=ins.readExcel(TestEntry.class, excelFile,sheetName);
            for(TestEntry entry : list){
                System.out.println("当前拿到的数据:"+entry.isRun()+","+entry.getParam());
                String activityIdParam=entry.getParam();
//调用

//                checkActivity(String activityId);
            }
            System.out.println("list长度:"+list.size());
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
