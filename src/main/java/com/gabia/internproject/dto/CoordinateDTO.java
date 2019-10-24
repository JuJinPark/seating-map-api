package com.gabia.internproject.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class CoordinateDTO {


    @NotNull(message = "위도를 입력하세요.")
    @ApiModelProperty(value="위도")
    private BigDecimal lat;
    @NotNull(message = "경도를 입력하세요.")
    @ApiModelProperty(value="경도")
    private BigDecimal lng;



}
