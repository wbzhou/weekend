package com.littleWeekend.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.littleWeekend.dao.master.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
@author :cathy
@time   :2019年03月13日
@comment:
**/
@Component
public class PointJob {
    @Autowired
    private ActivityDao activityDao;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//@Scheduled(cron="0 */1 * * * ?")
	public void doJob() {
		System.out.println("我来了："+ dateFormat.format(new Date()));




	}


}
