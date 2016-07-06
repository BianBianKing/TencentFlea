package com.tencentflea.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tencentflea.dao.api.UserDao;
import com.tencentflea.forms.User;
import com.tencentflea.service.api.UserService;

@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDaoImpl")
	UserDao userDao;

	@Override
	public User getUserByRTX(String rtxName) {
		return userDao.getUserByRTX(rtxName);
	}

	@Override
	public int saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

}
