package com.tencentflea.dao.api;

import com.tencentflea.forms.User;

public interface UserDao {
	public User getUserByRTX(String rtxName);

	public User getUserByUserId(long userId);

	public int saveUser(User user);

	public int updateUser(User user);
}
