package com.vti.project.repository;

import com.vti.project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Integer> {
    public Account findByUserName(String userName);
}
