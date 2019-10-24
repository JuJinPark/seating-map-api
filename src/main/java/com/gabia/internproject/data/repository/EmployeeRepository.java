package com.gabia.internproject.data.repository;

import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Seat;
import com.gabia.internproject.data.entity.SocialLoginEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    @Query("select DISTINCT  e from Employee e left join fetch e.role left join fetch e.department left join fetch e.seat")
    List<Employee> findAll();

    Optional<Employee> findByCompanyEmail(String companyEmail);
}
