package com.eAuction.Notification.service;

import com.eAuction.Notification.domain.UserEmail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEmailService {

    public void addEmail (String eamil, int id);

    public String findByUserId(int id);



}