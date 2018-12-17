package com.littleWeekend.dao.master;


import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 DAO 接口类
 *
 */
@Mapper
public interface UserDao {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    String findByName(String userName);
}
