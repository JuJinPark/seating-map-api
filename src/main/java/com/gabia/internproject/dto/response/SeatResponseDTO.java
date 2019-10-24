package com.gabia.internproject.dto.response;

import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SeatResponseDTO {

    @ApiModelProperty(value="자리 아이디")
    private long id;

//    private FloorResponseDTO floor;
    @ApiModelProperty(value="좌표")
    private CoordinateDTO coordinate;
    
 //   private EmployeeResponseDTO employee;

}
