package com.vti.project.service;

import com.vti.project.entity.Department;
import com.vti.project.form.CreatingDepartmentForm;
import com.vti.project.form.FilterDateDepartment;
import com.vti.project.form.UpdateDepartmentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDepartmentService {
    public Page<Department> getAllDepartments(Pageable pageable, String search, FilterDateDepartment filter);

    public void createDepartments(CreatingDepartmentForm form);
    public boolean isDepartmentExistsByName(String name);
    public Department getDepartmentById(int id);
    public void updateDepartment(int id, UpdateDepartmentForm form);
    public void deleteDepartment(int id);
    public void deleteDepartmentsByIds(List<Integer> ids);
    public List<Department> getDepartmentByName(String name);
}
