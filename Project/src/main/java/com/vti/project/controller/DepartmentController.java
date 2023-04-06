package com.vti.project.controller;

import com.vti.project.dto.DepartmentDTO;
import com.vti.project.entity.Department;
import com.vti.project.form.CreatingDepartmentForm;
import com.vti.project.form.FilterDateDepartment;
import com.vti.project.form.UpdateDepartmentForm;
import com.vti.project.service.IDepartmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "admin/departments")
@CrossOrigin(origins = "*")
@Validated
public class DepartmentController {
    @Autowired
    public IDepartmentService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<?> getAllDepartments(Pageable pageable,
                                               @RequestParam(value = "search", required = false) String search,
                                               FilterDateDepartment filter){
        Page<Department> departments = service.getAllDepartments(pageable, search, filter);
        List<DepartmentDTO> departemntDTOS = modelMapper.map(departments.getContent(), new TypeToken<List<DepartmentDTO>>(){}.getType());
        Page<DepartmentDTO> departmentDTOPage = new PageImpl<DepartmentDTO>(departemntDTOS, pageable, departments.getTotalElements());
        return new ResponseEntity<>(departmentDTOPage, HttpStatus.OK);
    }
    @GetMapping(value = "search")
    public List<DepartmentDTO> getDepartmentByName(@RequestParam(value = "name") String name){
        List<Department> departments = service.getDepartmentByName(name);
        List<DepartmentDTO> departmentDTOS = modelMapper.map(departments, new TypeToken<List<DepartmentDTO>>(){}.getType());
        return departmentDTOS;
    }
    @GetMapping(value = "/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable int id){
        Department department = service.getDepartmentById(id);
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        return departmentDTO;
    }
    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody @Valid CreatingDepartmentForm form){
        service.createDepartments(form);
        return new ResponseEntity<String>("Create Successfully", HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable int id, @RequestBody @Valid UpdateDepartmentForm form){
        service.updateDepartment(id,form);
        return new ResponseEntity<String>("Update Successfully", HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable int id){
        service.deleteDepartment(id);
        return new ResponseEntity<String>("Delete Sccuessfully", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByIds(@RequestParam (name = "ids") List<Integer> ids){
        service.deleteDepartmentsByIds(ids);
        return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
    }
}
