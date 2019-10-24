package com.gabia.internproject.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {

    @ApiModelProperty(value="직원 아이디")
    private long id;
    @ApiModelProperty(value="부서")
    private DepartmentResponseDTO department;
    @ApiModelProperty(value="권한")
    private RoleResponseDTO role;
    @ApiModelProperty(value="직원 이름")
    private String employeeName;
    @ApiModelProperty(value="사내이메일")
    private String companyEmail;
    @ApiModelProperty(value="내선번호")
    private int employeeExtension;
    @ApiModelProperty(value="사번")
    private String employeeNumber;
    @ApiModelProperty(value="자리")
    private SeatResponseDTO seat;


}
