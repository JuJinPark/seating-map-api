package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Department;
import com.gabia.internproject.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {


}
