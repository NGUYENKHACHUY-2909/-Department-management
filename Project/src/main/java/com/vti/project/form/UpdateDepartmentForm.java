package com.vti.project.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateDepartmentForm {
    @Length(min = 6, max = 50, message = "The length of the user name between 6 - 50 characters!")
    @NotBlank(message = "The name mustn't be null value")
    private String name;
}
