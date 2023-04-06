package com.vti.project.service;

import com.vti.project.entity.Account;
import com.vti.project.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private IAccountRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUserName(username);
        if(account == null){
            throw new UsernameNotFoundException(username);
        }
        return new User(account.getUserName(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString()));
    }


    @Override
    public Account getAccountByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
