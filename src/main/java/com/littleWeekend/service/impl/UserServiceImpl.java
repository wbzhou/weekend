package com.littleWeekend.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.domain.User;
import com.littleWeekend.util.HttpTookit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littleWeekend.dao.cluster.CityDao;
import com.littleWeekend.dao.master.UserDao;
import com.littleWeekend.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户业务实现层
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao; // 主数据源

    @SuppressWarnings("unused")
    @Autowired
    private CityDao cityDao; // 从数据源

    @Override
    public User findByActivityId(String activityId) {
        return userDao.findByActivityId(activityId);
    }

    @Override
    public String checkActivity(String activityId) {
        String url = "http://g.be.test.goago.cn/activity/getActivityById.do";
        Map<String, String> params = new HashMap<String, String>();
        params.put("activityId", "1D4URKI31EK3IT0AB2M103FPAH0012KH");
        //String postBodyJsonStr = JSONObject.toJSONString(params);
        String sendPostByJsonRes = HttpTookit.sendPost(url, params);
        System.out.println("json请求返回：" + sendPostByJsonRes);

        JSONObject parseObject = JSONObject.parseObject(sendPostByJsonRes);//把post请求的返回值 解析成json对象
        JSONObject dataObj = parseObject.getJSONObject("data");
        JSONObject dataObj1 = dataObj.getJSONObject("activity");
        String activityName="";
        if(dataObj!=null){

            activityName =dataObj1.getString("activityName");
        }


        System.out.println("拿到的activityName为：" + activityName);
        User use =userDao.findByActivityId(activityId);
        //String expRes =use.getActivityName();
        String expRes = "";
        if (use != null){
             expRes =use.getActivityName();
        }

        String remsg ="";
        if(activityName.equals(expRes)){
            remsg ="查到活动详情";
        }else{
            remsg ="活动不存在";
        }
        return remsg;

    }

    @Override
    public String checkUse(){
        return "这个是用于testNg的哈,说明testNg可以调用到service里";
    }


}

