package com.littleWeekend.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littleWeekend.dao.cluster.CityDao;
import com.littleWeekend.dao.master.UserDao;
import com.littleWeekend.service.UserService;

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
    public String findByName(String userName) {
        return userDao.findByName(userName);
    }
}
