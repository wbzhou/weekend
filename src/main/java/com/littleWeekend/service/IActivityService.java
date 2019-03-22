package com.littleWeekend.service;


import com.littleWeekend.domain.Activity;

/**
 * 用户业务接口层
 *
 */
public interface IActivityService {

    /**
     * 根据活动号获取活动名称，包括从库的地址信息
     *
     * @param activityId
     * @return
     */
    Activity findByActivityId(String activityId);

    String checkActivity(String activityId);
    String checkUse();
//    String checkIntegral();

}
