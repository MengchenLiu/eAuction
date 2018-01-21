package com.eAuction.Account.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.eAuction.Account.domain.User;
import com.eAuction.Account.Mapper.UserMapper;
import com.eAuction.Account.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	
	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public String checkLogin(String userName, String password) {
		User user = userMapper.findByName(userName);
		if(user == null) {
			return "UserName does not exit";
		}
		else {
			if(user.getPassword().equals(password)) {
				return "Login Success";
			}
			else return "Password is wrong";
		}
	}

	@Override
	public User findByName(String userName) {
		return userMapper.findByName(userName);
	}

	@Override
	public void deleteUser(String userName) {
		 userMapper.deleteUser(userName);
	}

	@Override
	public void updateUser(String userName,String role) {
		userMapper.updateUser(userName, role);
	}

	@Override
	public User findById(int id) {
		return userMapper.findById(id);
	}

	@Override
	public User updateInformation(String firstName, String lastName, String email, int id) {
		userMapper.updateInformation(firstName, lastName, email, id);	
		return userMapper.findById(id);
	}
	
	
		
}
