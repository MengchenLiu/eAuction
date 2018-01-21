package com.eAuction.Account.Service;

import org.springframework.stereotype.Service;

import com.eAuction.Account.domain.User;

@Service
public interface UserService {
	
	public void addUser(User user);
	
	
	public String checkLogin(String userName, String password);
	
	public User findByName(String userName);
	
	public void deleteUser(String userName);
	
	public void updateUser(String userName, String role);
	
	public User findById(int id);
	
	public User updateInformation(String firstName, String lastName, String email, int id);
	
}
