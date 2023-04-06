package com.vti.project.service;

import com.vti.project.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    public Account getAccountByUserName(String userName);
}
