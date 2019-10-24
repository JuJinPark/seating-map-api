package com.gabia.internproject.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class SeatEmployeeUpdateRequestDTO {

    @Min(value = 1,message = "직원 id 가 잘못되었습니다.")
    @ApiModelProperty(value="직원 id")
    long employeeId;
}
