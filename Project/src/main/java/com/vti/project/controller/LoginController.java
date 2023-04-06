package com.vti.project.controller;

import com.vti.project.entity.Account;
import com.vti.project.form.LoginInforDTO;
import com.vti.project.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/login")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private IAccountService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> login(Principal principal){
        String userName = principal.getName();
        Account account = service.getAccountByUserName(userName);
        LoginInforDTO loginInforDTO = modelMapper.map(account, LoginInforDTO.class);
        return new ResponseEntity<>(loginInforDTO, HttpStatus.OK);
    }
}
