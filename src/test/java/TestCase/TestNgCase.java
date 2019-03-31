package TestCase;

import com.littleWeekend.Application;
import com.littleWeekend.domain.TestEntry;
import com.littleWeekend.service.IActivityService;
import com.littleWeekend.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;


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

    @Test(groups = "serchActivity")
    public void testExcel(){
        String excelFile="/Users/weekend/zwb/doc/test1.xls";
        String sheetName="Sheet1";

        ExcelUtil ins=new ExcelUtil();
        try {

            List<TestEntry> list=ins.readExcel(TestEntry.class, excelFile,sheetName);
            for(TestEntry entry : list){
                System.out.println("当前拿到的数据:"+entry.isRun()+","+entry.getParam());
                String activityIdParam=entry.getParam();
//调用

                String res= IActivityService.checkActivity(activityIdParam);
                System.out.println("测试结果:" + res);
            }
            System.out.println("list长度:"+list.size());
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    @Test(groups = "interface1")
    public void testActivity(){String res = IActivityService.checkActivity("123");
        System.out.println("测试结果:" + res);
        System.out.println("测试结果:" + res);
//        userService.checkActivity("1D4URKI31EK3IT0AB2M103FPAH0012KH");
    }
}
