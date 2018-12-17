package com.weekend.littleWeekend;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.util.HttpTookit;

/**
@author :zhouwenbin
@time   :2018年12月15日
@comment:
**/
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//get
//		String getRes = HttpTookit.getMethod("www.baidu.com");
		
	//post!	
//		String url ="http://120.133.0.225/int_ticket/user/notify/fromBus";
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("orderId", "I544702613100208");
//		params.put("type", "-1");//不要改，只为-1
//		String sendPost = HttpTookit.sendPost(url, params);
//		System.out.println("返回："+sendPost);
		
		//post2
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
		String uid = dataObj.getString("uid");
		System.out.println("拿到的uid为："+uid);
	}

}
