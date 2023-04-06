package com.vti.project.form;

import com.vti.project.validation.DepartmentNameNotExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingDepartmentForm {
    @NotBlank(message = "The name mustn't be null value")
    @DepartmentNameNotExists
    @Length(min = 6, max = 50, message = "The length of the user name between 6 - 50 characters!")
    private String name;
    private int authorId;
}
