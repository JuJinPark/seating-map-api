package com.gabia.internproject.dto.request;

import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PlaceRequestDTO {

    @Min(value = 1,message = "층 아이디를 입력하세요.")
    @ApiModelProperty(value="층 아이디")
    private long floorId;

    @Min(value = 1,message = "장소타입 아이디를 입력하세요.")
    @ApiModelProperty(value="장소타입 아이디")
    private long placeTypeId;

    @NotBlank(message = "장소명이 있어야 합니다.")
    @ApiModelProperty(value="장소명")
    private String placeName;

    @Valid
    @NotNull(message = "좌표값이 있어야 합니다.")
    @ApiModelProperty(value="좌표값")
    private CoordinateDTO coordinate;


}
