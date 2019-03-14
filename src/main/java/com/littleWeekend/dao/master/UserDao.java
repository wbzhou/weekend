package com.littleWeekend.dao.master;


import com.littleWeekend.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 DAO 接口类
 *
 */
@Mapper
public interface UserDao {

    /**
     *
     *
     *
     * @return
     */
    User findByActivityId(String activityId);
}
