package com.vti.project.form;

import com.vti.project.entity.Account;
import lombok.Data;

@Data
public class LoginInforDTO {
    private String id;
    private String fullName;
    private Account.Role role;
}
