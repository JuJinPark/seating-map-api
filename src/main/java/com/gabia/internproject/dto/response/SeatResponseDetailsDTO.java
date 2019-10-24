package com.gabia.internproject.dto.response;

import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SeatResponseDetailsDTO extends SeatResponseDTO {

    @ApiModelProperty(value="층")
    private FloorResponseDTO floor;
    @ApiModelProperty(value="직원")
    private EmployeeResponseDTO employee;

}

