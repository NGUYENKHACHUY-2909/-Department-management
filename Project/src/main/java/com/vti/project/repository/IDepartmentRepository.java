package com.vti.project.repository;

import com.vti.project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    public boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE from Department where id in (:ids)")
    public void deleteDepartmentByIds(@Param("ids") List<Integer> ids);

    @Modifying
    @Query("select d from Department  d where d.name like (:name)")
    public List<Department> getAllByName(String name);
}
