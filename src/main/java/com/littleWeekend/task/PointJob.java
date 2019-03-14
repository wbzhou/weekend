package com.littleWeekend.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.littleWeekend.dao.master.UserDao;
import com.littleWeekend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.util.HttpTookit;

/**
@author :cathy
@time   :2019年03月13日
@comment:
**/
@Component
public class PointJob {
    @Autowired
    private UserDao userDao;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//@Scheduled(cron="0 */1 * * * ?")
	public void doJob() {
		System.out.println("我来了："+ dateFormat.format(new Date()));




	}


}
