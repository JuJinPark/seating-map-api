package com.gabia.internproject.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DepartmentResponseDTO {

    @ApiModelProperty(value="부서 아이디")
    private long id;
    @ApiModelProperty(value="부서 이름")
    private String departmentName;
}
