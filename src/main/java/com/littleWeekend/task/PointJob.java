package com.littleWeekend.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.util.HttpTookit;

/**
@author :zhouwenbin
@time   :2018年12月15日
@comment:
**/
@Component
public class PointJob {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Scheduled(cron="0 */1 * * * ?")
	public void doJob() {
		System.out.println("我来了："+ dateFormat.format(new Date()));
		
		checkInterFaceValid();
		
		
	}

	private void checkInterFaceValid() {
		String url="http://120.133.0.225/int_ticket/api/order/basicInfo";
		Map<String, String> params=new HashMap<String, String>();
		params.put("orderId", "I544702613100208");
		String postBodyJsonStr = JSONObject.toJSONString(params);
		String sendPostByJsonRes = HttpTookit.sendPostByJson(url, postBodyJsonStr);
		System.out.println("json请求返回："+sendPostByJsonRes);
		
		JSONObject parseObject = JSONObject.parseObject(sendPostByJsonRes);//把post请求的返回值 解析成json对象
		JSONObject dataObj = parseObject.getJSONObject("data");
		float floatValue = dataObj.getFloatValue("totalPrice");
		float userId = dataObj.getFloatValue("userId");
		String flag=dataObj.getString("success");
		if(null!=flag&& flag.equals("fail")){//如果接口报异常，比如 fail。
			//发邮件说接口错了
		}
		
	}
}
