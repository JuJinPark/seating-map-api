package com.gabia.internproject.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gabia.internproject.dto.CoordinateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatUpdateRequestDTO extends SeatRequestDTO {

    @Min(value = 1,message = "자리 아이디를 입력하세요.")
    @ApiModelProperty(value="자리 아이디")
    private long id;

}
