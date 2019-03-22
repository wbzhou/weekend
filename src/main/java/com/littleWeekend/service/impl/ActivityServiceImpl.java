package com.littleWeekend.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.littleWeekend.domain.Activity;
import com.littleWeekend.util.HttpTookit;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littleWeekend.dao.cluster.CityDao;
import com.littleWeekend.dao.master.ActivityDao;
import com.littleWeekend.service.IActivityService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户业务实现层
 *
 */
@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityDao activityDao; // 主数据源


    @SuppressWarnings("unused")
    @Autowired
    private CityDao cityDao; // 从数据源

    public ActivityServiceImpl() {
    }

    @Override
    public Activity findByActivityId(String activityId) {
        return activityDao.findByActivityId(activityId);
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
        String activityName = "";
        if (dataObj != null) {

            activityName = dataObj1.getString("activityName");
        }


        System.out.println("拿到的activityName为：" + activityName);
        Activity activity = activityDao.findByActivityId(activityId);
        //String expRes =use.getActivityName();
        String expRes = "";
        if (activity != null) {
            expRes = activity.getActivityName();
            Activity use = activityDao.findByActivityId(activityId);
//            String expRes =use.getActivityName();
        if (use != null) {
            expRes = use.getActivityName();
        }

            String remsg = "";
            if (activityName.equals(expRes)) {
                remsg = "查到活动详情";
            } else {
                remsg = "活动不存在";
            }
            return remsg;

        }
        return expRes;
    }

//    @Override
//    public String checkUse() {
//            int i = 1;
//            switch (i) {
//                case 0:
//                    System.out.println("0");
//                    break;
//                case 1:
//                    System.out.println("1");
//                case 2:
//                    System.out.println("4");
//                default:
//                    System.out.println("defa");
//
//
//            }
//            return "说明testNg可以调用到service里";
//        }

    public String checkUse() {
        return "这个是用于testNg的哈,说明testNg可以调用到service里";
    }

//    @Override
//    public String checkIntegral() {
//        String url = "http://acquirer.test.goago.cn/openPlatform/tradeIntegral.do";
//        Map<String,String> params =new HashMap<>();
//        params.put("billid","1");
//        params.put("moblie","15110262561");
//        params.put("signKey","1");
//
//    }
}
