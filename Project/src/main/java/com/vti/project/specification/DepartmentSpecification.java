package com.vti.project.specification;

import com.vti.project.entity.Department;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

@AllArgsConstructor
public class DepartmentSpecification implements org.springframework.data.jpa.domain.Specification<Department> {
    private String field;
    private String operator;
    private Object value;
    @Override
    public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(operator.equalsIgnoreCase("like")){
            return criteriaBuilder.like(root.get(field), "%"+value.toString()+"%");
        }
        if(operator.equalsIgnoreCase(">=")){
            if(value instanceof Date){
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), (Date) value);
            }
        }
        if(operator.equalsIgnoreCase("<=")){
            if(value instanceof Date){
                Date today = (Date) value;
                Date tomorrow = new Date(today.getTime()+ (1000 * 60 * 60 * 24));
                return criteriaBuilder.lessThan(root.get(field), tomorrow);
            }
        }
        return null;
    }
}
