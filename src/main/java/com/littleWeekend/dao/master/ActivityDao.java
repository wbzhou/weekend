package com.littleWeekend.dao.master;


import com.littleWeekend.domain.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 DAO 接口类
 *
 */
@Mapper
public interface ActivityDao {

    /**
     *
     *
     *
     * @return
     */
    Activity findByActivityId(String activityId);
}
