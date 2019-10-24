package com.gabia.internproject.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.internproject.config.TestConfig;
import com.gabia.internproject.data.entity.*;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.dto.response.RoleResponseDTO;
import com.gabia.internproject.service.EmployeeService;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import com.gabia.internproject.util.JwtTokenProvider;
import com.gabia.internproject.util.ObjectMaping.MapStruct.MapStructUtil;
import com.gabia.internproject.util.ObjectMaping.ObjectMappingUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@Import(TestConfig.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ObjectMappingUtil mapper;

    private static List<Role> roleTable = new ArrayList<>();
    private static List<Department> departmentTable = new ArrayList<>();

    private List<Employee> empTable = new ArrayList<>();

    private Cookie jwt;

    @BeforeClass
    public static void init() throws Exception {

        mapper = new MapStructUtil();
        roleTableSettings();
        departmentTableSettings();


    }

    @Before
    public void createCookie() {
        JwtTokenProvider jwtTokenProvider2 = new JwtTokenProvider();
        EmployeeResponseDTO employee = new EmployeeResponseDTO();
        employee.setId(1);
        employee.setEmployeeName("test");
        RoleResponseDTO role = new RoleResponseDTO();
        role.setRoleType("ADMIN");
        employee.setRole(role);
        String jwtString = jwtTokenProvider.createToken(employee, OAuthAPIProvider.HIWORKS);
        jwt = new Cookie(OAuthConstants.SEAT_API_JWT.name(), jwtString);
    }

    private static void roleTableSettings() {

        Role user = Role.createNewRole("user");
        Role admin = Role.createNewRole("admin");
        roleTable.add(user);
        roleTable.add(admin);

    }

    private static void departmentTableSettings() {

        Department HR = new Department();
        HR.setDepartmentName("인사");
        Department Dev = new Department();
        Dev.setDepartmentName("개발");
        departmentTable.add(HR);
        departmentTable.add(Dev);

    }


    private Department findDepartmentById(long depId) {

        return departmentTable.get((int) depId - 1);
    }

    private Role findRoleById(long roleId) {

        return roleTable.get((int) roleId - 1);
    }


    private Employee createEmpForTest(String name, String email, long depId, long roleId, String empNum) {
        Employee emp = new Employee();
        emp.setEmployeeName(name);
        emp.setCompanyEmail(email);
        emp.setDepartment(findDepartmentById(depId));
        emp.setRole(findRoleById(roleId));
        emp.setEmployeeNumber(empNum);
        empTable.add(emp);
        return emp;
    }


    public EmployeeResponseDTO convertToResponseDTO(Employee emp) {
        return mapper.convertToDTO(emp, EmployeeResponseDTO.class);
    }

    public List<EmployeeResponseDTO> convertToResponseDTO(List<Employee> emp) {
        return mapper.convertToDTO(emp, EmployeeResponseDTO.class);
    }


    @Test
    public void getAllEmployees() throws Exception {

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

        given(employeeService.getAllEmployees()).willReturn(convertToResponseDTO(empTable));

        this.mvc.perform(MockMvcRequestBuilders.get("/employees").cookie(jwt).characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[*].department.departmentName", containsInAnyOrder(findDepartmentById(emp1DepartmentId).getDepartmentName(), findDepartmentById(emp2DepartmentId).getDepartmentName())))
                .andExpect(jsonPath("$[*].role.roleType", containsInAnyOrder(findRoleById(emp1RoleId).getRoleType(), findRoleById(emp2RoleId).getRoleType())))
                .andExpect(jsonPath("$[*].employeeName", containsInAnyOrder(emp1Name, emp2Name)))
                .andExpect(jsonPath("$[*].employeeNumber", containsInAnyOrder(emp1EmployeeNumber, emp2EmployeeNumber)))
                .andExpect(jsonPath("$[*].companyEmail", containsInAnyOrder(emp1CompanyEmail, emp2CompanyEmail)));
    }


}
