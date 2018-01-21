package com.eAuction.Notification.service;

import com.eAuction.Notification.Mapper.UserEmailMapper;
import com.eAuction.Notification.domain.UserEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserEmailServiceImpl implements UserEmailService{

    @Autowired
    UserEmailMapper userEmailMapper;


    @Override
    public void addEmail (String email, int id) {
        userEmailMapper.addEmail(email,id);
    }


    @Override
    public String findByUserId(int id) {
        return userEmailMapper.findByUserId(id);
    }



}