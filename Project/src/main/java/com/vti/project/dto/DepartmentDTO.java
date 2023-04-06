package com.vti.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vti.project.entity.Account;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Data
public class DepartmentDTO extends RepresentationModel<DepartmentDTO> {
    private int id;
    private String name;
    private  AccountDTO author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creatDate;
    private List<AccountDTO> accounts;


    @Data
    public static class AccountDTO {
        private Account.Role role;
        private int id;
        private String userName;
        private String fullName;
        private String email;
    }
}
