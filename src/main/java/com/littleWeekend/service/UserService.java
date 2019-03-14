package com.littleWeekend.service;


import com.littleWeekend.domain.User;

/**
 * 用户业务接口层
 *
 */
public interface UserService {

    /**
     * 根据活动号获取活动名称，包括从库的地址信息
     *
     * @param activityId
     * @return
     */
    User findByActivityId(String activityId);

    String checkActivity(String activityId);
    String checkUse();
}
