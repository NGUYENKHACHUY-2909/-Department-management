package com.vti.project.service;

import com.vti.project.entity.Account;
import com.vti.project.entity.Department;
import com.vti.project.form.CreatingDepartmentForm;
import com.vti.project.form.FilterDateDepartment;
import com.vti.project.form.UpdateDepartmentForm;
import com.vti.project.repository.IAccountRepository;
import com.vti.project.repository.IDepartmentRepository;
import com.vti.project.specification.DepartmentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.module.FindException;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService{
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IAccountRepository accountRepository;


    @SuppressWarnings("deprecation")
    @Override
    public Page<Department> getAllDepartments(Pageable pageable, String search, FilterDateDepartment filter) {
        Specification<Department> where = null;
        if(!StringUtils.isEmpty(search)){
            DepartmentSpecification specification = new DepartmentSpecification("name", "like", search);
            where = Specification.where(specification);
        }
        if(filter != null && filter.getMinDate() != null){
            DepartmentSpecification minSpecification = new DepartmentSpecification("creatDate", ">=", filter.getMinDate());
            if(where == null){
                where = Specification.where(minSpecification);
            }
            else {
                where = where.and(minSpecification);
            }
        }
        if(filter != null && filter.getMaxDate() != null){
            DepartmentSpecification maxSpecification = new DepartmentSpecification("creatDate", "<=", filter.getMaxDate());
            if(where == null){
                where = Specification.where(maxSpecification);
            }
            else {
                where = where.and(maxSpecification);
            }
        }
        return departmentRepository.findAll(where, pageable);
    }

    @Override
    public void createDepartments(CreatingDepartmentForm form) {
        Account author = accountRepository.findById(form.getAuthorId()).get();
        Department department = new Department(form.getName());
        department.setAuthor(author);
        departmentRepository.save(department);
    }

    @Override
    public boolean isDepartmentExistsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id).orElseThrow(()->new FindException("Khong tim thay Department co id: "+id));
    }

    @Override
    @Transactional
    public void updateDepartment(int id, UpdateDepartmentForm form) {
        Department department = getDepartmentById(id);
        department.setName(form.getName());
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }

    public void deleteDepartmentsByIds(List<Integer> ids){
        departmentRepository.deleteDepartmentByIds(ids);
    }

    @Override
    public List<Department> getDepartmentByName(String name) {
        return departmentRepository.getAllByName(name);
    }
}
