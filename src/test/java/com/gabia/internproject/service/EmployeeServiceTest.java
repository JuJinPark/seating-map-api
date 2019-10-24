package com.gabia.internproject.service;

import com.gabia.internproject.data.entity.Department;
import com.gabia.internproject.data.entity.Employee;
import com.gabia.internproject.data.entity.Role;
import com.gabia.internproject.data.repository.*;
import com.gabia.internproject.dto.response.*;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;

//import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    @Before
    public void a_init() throws Exception {
        roleTableSettings();
        departmentTableSettings();


    }

    @After
    public void removeAll() throws Exception {
        employeeRepository.deleteAll();

    }

    public void roleTableSettings() {
        Role user = Role.createNewRole("user");
        Role admin = Role.createNewRole("admin");
        roleRepository.save(user);
        roleRepository.save(admin);
    }

    public void departmentTableSettings() {

        Department HR = new Department();
        HR.setId(1);
        HR.setDepartmentName("인사");
        Department Dev = new Department();
        Dev.setId(2);
        Dev.setDepartmentName("개발");

        departmentRepository.save(HR);
        departmentRepository.save(Dev);

    }


    private Employee createEmpForTest(String name, String email, long depId, long roleId, String empNum) {
        Employee emp = new Employee();
        emp.setEmployeeName(name);
        emp.setCompanyEmail(email);
        emp.setDepartment(departmentRepository.findById(depId).orElse(null));
        emp.setRole(roleRepository.findById(roleId).orElse(null));
        emp.setEmployeeNumber(empNum);

        return employeeRepository.save(emp);
    }

    private void printStartLine() {

        System.out.println("---------------------------start-----------------------------------------");

    }

    private void printEndLine() {

        System.out.println("---------------------------end-----------------------------------------");

    }


    @Test
//    @Transactional
    public void getAllFloors() {
        final String emp1Name = "A";
        final String emp1CompanyEmail = "A@gabia.com";
        final long emp1DepartmentId = 1;
        final long emp1RoleId = 1;
        final String emp1EmployeeNumber = "A123";
        Employee emp1 = createEmpForTest(emp1Name, emp1CompanyEmail, emp1DepartmentId, emp1RoleId, emp1EmployeeNumber);

        final String emp2Name = "B";
        final String emp2CompanyEmail = "B@gabia.com";
        final long emp2DepartmentId = 2;
        final long emp2RoleId = 2;
        final String emp2EmployeeNumber = "B123";
        Employee emp2 = createEmpForTest(emp2Name, emp2CompanyEmail, emp2DepartmentId, emp2RoleId, emp2EmployeeNumber);

        printStartLine();
        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();
        printEndLine();

        Assert.assertThat(result.size(), is(2));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("employeeName", Matchers.is(emp1Name)),
                hasProperty("employeeName", Matchers.is(emp2Name))
        ));

        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("employeeNumber", Matchers.is(emp1EmployeeNumber)),
                hasProperty("employeeNumber", Matchers.is(emp2EmployeeNumber))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("role", hasProperty("roleType", Matchers.is(emp1.getRole().getRoleType()))),
                hasProperty("role", hasProperty("roleType", Matchers.is(emp2.getRole().getRoleType())))
        ));
        Assert.assertThat(result, containsInAnyOrder(
                hasProperty("department", hasProperty("departmentName", Matchers.is(emp1.getDepartment().getDepartmentName()))),
                hasProperty("department", hasProperty("departmentName", Matchers.is(emp2.getDepartment().getDepartmentName())))
        ));

    }


}
