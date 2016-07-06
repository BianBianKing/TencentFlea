package com.tencentflea.service.api;

import com.tencentflea.forms.User;

public interface UserService {
	public User getUserByRTX(String rtxName);

	public int saveUser(User user);

	public int updateUser(User user);
}
