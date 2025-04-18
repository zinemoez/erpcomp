package com.erp.erp.repository;
import com.erp.erp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, String> {
    Optional<Department> findById(String id);


}
