package com.littleWeekend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.util.EncriptMD5;
import com.littleWeekend.util.HttpTookit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: junting
 * @CreateDate: 2019/3/20$ 13:53$
 * @Version: 1.0
 */
public class IntegralServiceImpl {

    public static void main(String[] args) {
        IntegralServiceImpl integralService =new IntegralServiceImpl();
        integralService.checkIntegral();

      }
    public String checkIntegral() {
        String url = "http://acquirer.test.goago.cn/openPlatform/tradeIntegral.do";
        Map<String,String> params =new HashMap<>();
        params.put("billId","97AFD58BFF44E9D70CCBFD219EFD7F0A");
        params.put("moblie","15110262561");
        //IntegralServiceImpl integralService;

        String signKeyValue= EncriptMD5.MD5("15110262561GAG20181114LH");
        params.put("signKey",signKeyValue);
        String integralRes = HttpTookit.sendPost(url,params);
        System.out.println("json请求返回：" + integralRes);

        JSONObject jsonObject = JSONObject.parseObject(integralRes);

        JSONObject jsonObject1=jsonObject.getJSONObject("data");
        JSONObject jsonObject2=jsonObject1.getJSONObject("result");
        //JSONObject jsonObject3=jsonObject2.getJSONObject("goodsDetails");

        String jsonStr= jsonObject2.getString("goodsDetails");


        List<JSONObject> jsonObjectList = JSON.parseArray(jsonStr,JSONObject.class);
        //List<String>  list = new ArrayList<>();

        //List<ResourcesMiddleEntity> list = new ArrayList<>();
        for(JSONObject jsonObject4:jsonObjectList){
            String name=jsonObject4.getString("name");
            String price=jsonObject4.getString("price");
             //list.add(name);
             //list.add(price);
             if(name!=null&price!=null){
                 System.out.println("商品名称:"+name);
                 System.out.println("价格:"+price);

             }

        }

        return "测试结束";
    }
}
