package com.gabia.internproject.dto.request;


import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SeatRequestDTO {

    @Min(value = 1,message = "층 아이디를 입력하세요.")
    @ApiModelProperty(value="층 아이디")
    private long floorId;

    @Valid
    @NotNull(message = "좌표값이 있어야 합니다.")
    @ApiModelProperty(value="좌표값")
    private CoordinateDTO coordinate;



}
