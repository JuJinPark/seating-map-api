package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.repository.EmployeeRepository;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.exception.customExceptions.EmployeeNotFoundException;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class EmployeeService {

    ObjectMappingUtil mapper;

    EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(ObjectMappingUtil mapper, EmployeeRepository employeeRepository){
        this.mapper=mapper;
        this.employeeRepository=employeeRepository;
    }

    @Transactional
    public List<EmployeeResponseDTO> getAllEmployees() {
        return mapper.convertToDTO(employeeRepository.findAll(),EmployeeResponseDTO.class);

    }

    @Transactional
    public EmployeeResponseDTO getEmployeeByEmail(String companyEmail){

        return mapper.convertToDTO(getEmployeeEntityByEmail(companyEmail),EmployeeResponseDTO.class);
    }



    public Employee getEmployeeEntityById(long employeeID){

        Employee employee = employeeRepository.findById(employeeID)
                        .orElseThrow(()->new EmployeeNotFoundException("requested employeeId doesn't exist"));

        return employee;
    }

    public Employee getEmployeeEntityByEmail(String companyEmail){
        Employee employee = employeeRepository.findByCompanyEmail(companyEmail)
                .orElseThrow(()->new EmployeeNotFoundException("requested employeeId doesn't exist"));

        return employee;

    }





}
